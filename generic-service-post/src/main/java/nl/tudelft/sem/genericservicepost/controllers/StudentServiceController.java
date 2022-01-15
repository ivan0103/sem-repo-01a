package nl.tudelft.sem.genericservicepost.controllers;

import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentgenericpost")
public class StudentServiceController {

    @Autowired
    private transient StudentService genericPostService;

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
