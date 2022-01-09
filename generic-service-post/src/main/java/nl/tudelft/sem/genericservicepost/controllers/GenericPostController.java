package nl.tudelft.sem.genericservicepost.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Collection;
import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.services.GenericPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genericpost")
public class GenericPostController {

    private final transient String filterName = "postFilter";

    private final transient String userType = "user";

    @Autowired
    private transient GenericPostService genericPostService;

    /**
     * Create post mapping jackson value.
     *
     * @param post the post
     * @return the mapping jackson value
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> createGenericPost(
            @Valid @RequestBody GenericPost post) {
        GenericPost savedPost = genericPostService.createGenericPost(post);
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
    public ResponseEntity<MappingJacksonValue> editPost(
            @Valid @RequestBody GenericPost post,
            @RequestParam("genericPostId") String postId) {
        GenericPost editedPost = genericPostService.editGenericPost(post, postId);

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
     * Gets all posts.
     *
     * @return all posts in repository
     */
    @GetMapping(value = "/getall")
    public ResponseEntity<MappingJacksonValue> getAll() {
        Collection<GenericPost> found = genericPostService.getAll();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept(userType);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(filterName, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(found);
        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.FOUND);
    }

    // Will be moved to the Student Offer Controller than Marie made, after her merge.
    @PostMapping("/getStudentsByPost")
    public ResponseEntity<Set<UserImpl>> getStudentsByPost(
        @Valid @RequestBody GenericPost post) {
        Set<UserImpl> result = genericPostService.retrieveStudentsInPost(post);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("/setSelectedStudent")
    public ResponseEntity<UserImpl> setSelectedStudent(
            @Valid @RequestBody UserImpl student, GenericPost post) {
        UserImpl result = genericPostService.setSelectedStudent(student, post);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
