package nl.tudelft.sem.studentservicepost.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Post controller.
 */
@RestController
@RequestMapping("/servicepost")
public class PostController {

    private final transient String filterName = "postFilter";
    
    private final transient String userType = "user";
    @Autowired
    private transient PostService postService;

    /**
     * Instantiates a new Post controller.
     *
     * @param postService the post service
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Create post mapping jackson value.
     *
     * @param post the post
     * @return the mapping jackson value
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postService.createPost(post);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(savedPost);
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.CREATED);
    }

    /**
     * Edit post mapping jackson value.
     *
     * @param post   the post
     * @param postId the post id
     * @return the mapping jackson value
     */
    @PatchMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> editPost(@Valid @RequestBody Post post,
                                                        @RequestParam("postId") String postId) {
        Post editedPost = postService.editPost(post, postId);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(editedPost);
        mappingJacksonValue.setFilters(filterProvider);


        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
            .body(mappingJacksonValue);
    }

    /**
     * Gets post by post id.
     *
     * @param postId the post id
     * @return the post by post id
     */
    @GetMapping(value = "/retrieve/{postId}")
    public ResponseEntity<MappingJacksonValue> getPostByPostId(@PathVariable String postId) {
        Post post = postService.getById(postId);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(post);
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.FOUND);
    }

    /**
     * Search posts by keywords.
     *
     * @param keywords the keywords
     * @return the response
     */
    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> searchPostsByKeywords(
        @RequestBody Set<String> keywords) {
        Collection<Post> found = new HashSet<>();
        for (String keyword : keywords) {
            found.addAll(postService.searchByKeyword(keyword));
        }

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(found);
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.FOUND);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping(value = "/getall")
    public ResponseEntity<MappingJacksonValue> getAll() {
        Collection<Post> found = postService.getAll();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(found);
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.FOUND);
    }

}
