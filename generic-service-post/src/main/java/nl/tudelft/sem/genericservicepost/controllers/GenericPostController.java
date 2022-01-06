package nl.tudelft.sem.genericservicepost.controllers;

import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.services.GenericPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genericpost")
public class GenericPostController {

    @Autowired
    private transient GenericPostService genericPostService;

    @PostMapping("/create")
    public ResponseEntity<GenericPost> createGenericPost(@Valid @RequestBody GenericPost post) {
        GenericPost savedPost = genericPostService.createGenericPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // Will be moved to the Student Offer Controller than Marie made, after her merge.
    @PostMapping("/getStudentsByPost")
    public ResponseEntity<Set<StudentOffer>> getStudentsByPost(
        @Valid @RequestBody GenericPost post) {
        Set<StudentOffer> result = genericPostService.retrieveStudentsInPost(post);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }
}
