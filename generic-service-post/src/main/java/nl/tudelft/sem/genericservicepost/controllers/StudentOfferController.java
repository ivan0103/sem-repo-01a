package nl.tudelft.sem.genericservicepost.controllers;

import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.services.StudentOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentoffers")
public class StudentOfferController {

    @Autowired
    private transient StudentOfferService studentOfferService;

    /**
     * Create student offer response entity.
     *
     * @param studentOffer  the student offer
     * @param genericPostId the generic post id
     * @return the response entity
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentOffer> createStudentOffer(
        @Valid @RequestBody StudentOffer studentOffer,
        @RequestParam("genericPostId") String genericPostId) {
        StudentOffer savedStudentOffer =
            studentOfferService.createStudentOffer(studentOffer, genericPostId);
        return new ResponseEntity<>(savedStudentOffer, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getByGenericPostId")
    public ResponseEntity<Set<StudentOffer>> getStudentOffers(
        @Valid @RequestParam("genericPostId") String genericPostId) {
        Set<StudentOffer> result = studentOfferService.getByGenericPostId(genericPostId);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    // Will be moved to the Student Offer Controller than Marie made, after her merge.
    @PostMapping("/getStudentsByPost")
    public ResponseEntity<Set<UserImpl>> getStudentsByPost(
            @Valid @RequestBody GenericPost post) {
        Set<UserImpl> result = studentOfferService.retrieveStudentsInPost(post);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("/setSelectedStudent")
    public ResponseEntity<UserImpl> setSelectedStudent(
            @Valid @RequestBody UserImpl student, GenericPost post) {
        UserImpl result = studentOfferService.setSelectedStudent(student, post);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
