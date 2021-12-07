package nl.tudelft.sem.genericservicepost.controllers;

import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
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

    @PostMapping("/creategenericpost")
    public ResponseEntity<GenericPost> createGenericPost(@Valid @RequestBody GenericPost post) {
        GenericPost savedPost = genericPostService.createGenericPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
}
