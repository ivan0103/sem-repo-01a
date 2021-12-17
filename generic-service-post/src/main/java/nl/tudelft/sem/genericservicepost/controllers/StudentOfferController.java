package nl.tudelft.sem.genericservicepost.controllers;

import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.services.StudentOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/studentoffers")
public class StudentOfferController {

    @Autowired
    private transient StudentOfferService studentOfferService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentOffer> createStudentOffer (@Valid @RequestBody StudentOffer studentOffer,
                                      @RequestParam("genericPostId") String genericPostId) {
        StudentOffer savedStudentOffer = studentOfferService.createStudentOffer(studentOffer, genericPostId);
        return new ResponseEntity<>(savedStudentOffer, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getByGenericPostId")
    public ResponseEntity<Set<StudentOffer>> getStudentOffers(
            @Valid @RequestParam("genericPostId") String genericPostId) {
        Set<StudentOffer> result = studentOfferService.getByGenericPostId(genericPostId);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }
}
