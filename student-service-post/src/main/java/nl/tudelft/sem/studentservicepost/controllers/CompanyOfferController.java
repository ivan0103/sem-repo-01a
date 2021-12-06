package nl.tudelft.sem.studentservicepost.controllers;

import javax.validation.Valid;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.services.CompanyOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offers")
public class CompanyOfferController {

    @Autowired
    private transient CompanyOfferService companyOfferService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyOffer> createOffer(
            @Valid @RequestBody CompanyOffer companyOffer,
            @RequestParam("postId") String postId) {
        CompanyOffer savedCompanyOffer = companyOfferService.createOffer(companyOffer, postId);
        return new ResponseEntity<CompanyOffer>(savedCompanyOffer, HttpStatus.CREATED);
    }
}
