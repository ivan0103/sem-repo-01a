package nl.tudelft.sem.genericservicepost.controllers;

import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.services.GenericPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/genericpost")
public class GenericPostController {

    @Autowired
    private transient GenericPostService genericPostService;

    /**
     * Creates a generic Post.
     *
     * @param post the post.
     * @return the post.
     */
    @PostMapping("/create")
    public ResponseEntity<GenericPost> createGenericPost(@Valid @RequestBody GenericPost post) {
        GenericPost savedPost = genericPostService.createGenericPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    /**
     * Gets post by id.
     *
     * @param postId the post id.
     * @return the post.
     */
    @GetMapping("/retrieve/{postId}")
    public ResponseEntity<GenericPost> getPostById(@PathVariable String postId) {
        GenericPost post = genericPostService.getById(postId);
        return new ResponseEntity<>(post, HttpStatus.FOUND);
    }

    /**
     * Gets all students that have signed up for a generic post.
     *
     * @param postId the generic post id.
     * @return set of users, students that are signed up for the post.
     */
    @GetMapping("/getStudentsByPost/{postId}")
    public ResponseEntity<Set<UserImpl>> getStudentsByPost(@PathVariable String postId) {
        GenericPost post = genericPostService.getById(postId);
        Set<UserImpl> result = genericPostService.retrieveStudentsInPost(post);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Company choosing a student.
     *
     * @param netId the netId of the student that was selected.
     * @param postId the post to which the student is picked for.
     * @return the user.
     */
    @PutMapping("/setSelectedStudent/{netId}/{postId}")
    public ResponseEntity<UserImpl> setSelectedStudent(
            @PathVariable String netId,
            @PathVariable String postId) {
        GenericPost post = genericPostService.getById(postId);
        UserImpl student = genericPostService.getStudentById(netId);
        UserImpl result = genericPostService.setSelectedStudent(student, post);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * User signing up for a post.
     *
     * @param netId the user id.
     * @param postId the post to which the user signs up.
     * @return the user.
     */
    @PutMapping("/signUpStudent/{netId}/{postId}")
    public ResponseEntity<UserImpl> signUpStudent(
            @PathVariable String netId,
            @PathVariable String postId) {
        GenericPost post = genericPostService.getById(postId);
        UserImpl result = genericPostService.signUp(netId, post);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
