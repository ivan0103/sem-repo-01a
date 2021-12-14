package nl.tudelft.sem.studentservicepost.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicepost")
public class PostController {

    @Autowired
    private transient PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> editPost(@Valid @RequestBody Post post,
                                         @RequestParam("postId") String postId) {
        Post editedPost = postService.editPost(post, postId);
        return new ResponseEntity<>(editedPost, HttpStatus.ACCEPTED);
    }


    /**
     * Search posts by keywords.
     *
     * @param keywords the keywords
     * @return the response
     */
    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Post>> searchPostsByKeywords(
        @RequestBody Set<String> keywords) {

        Collection<Post> found = new HashSet<>();
        for (String keyword : keywords) {
            found.addAll(postService.searchByKeyword(keyword));
        }
        return new ResponseEntity<>(found, HttpStatus.FOUND);
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<Collection<Post>> searchPostsByKeywords() {
        Collection<Post> found = postService.getAll();
        return new ResponseEntity<>(found, HttpStatus.FOUND);
    }

}
