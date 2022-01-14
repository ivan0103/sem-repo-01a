package nl.tudelft.sem.genericservicepost.controllers;

import java.util.Collection;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.services.GenericPostHelper;
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

    @Autowired
    private transient GenericPostHelper genericPostHelper;

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
        return new ResponseEntity<>(genericPostHelper
                .mappingJacksonPost(savedPost, filterName, userType),
                HttpStatus.CREATED);
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
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(genericPostHelper.mappingJacksonPost(editedPost, filterName, userType));
    }

    /**
     * Gets all posts.
     *
     * @return all posts in repository
     */
    @GetMapping(value = "/getall")
    public ResponseEntity<MappingJacksonValue> getAll() {
        Collection<GenericPost> found = genericPostService.getAll();
        return new ResponseEntity<>(genericPostHelper
                .mappingJacksonCollection(found, filterName, userType),
                HttpStatus.FOUND);
    }


}
