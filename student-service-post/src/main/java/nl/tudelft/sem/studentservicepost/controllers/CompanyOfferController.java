package nl.tudelft.sem.studentservicepost.controllers;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Contract;
import nl.tudelft.sem.studentservicepost.services.CompanyOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.client.RestTemplate;

/**
 * The type Company offer controller.
 */
@RestController
@RequestMapping("/offers")
public class CompanyOfferController {

    @Autowired
    private transient CompanyOfferService companyOfferService;

    /**
     * Create offer response entity.
     *
     * @param companyOffer the company offer
     * @param postId       the post id
     * @return the response entity
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyOffer> createOffer(
        @Valid @RequestBody CompanyOffer companyOffer,
        @RequestParam("postId") String postId) {
        CompanyOffer savedCompanyOffer = companyOfferService.createOffer(companyOffer, postId);
        return new ResponseEntity<>(savedCompanyOffer, HttpStatus.CREATED);
    }

    /**
     * Gets offers.
     *
     * @param postId the post id
     * @return the offers
     */
    @GetMapping(value = "/getByPostId")
    public ResponseEntity<Set<CompanyOffer>> getOffers(
        @Valid @RequestParam("postId") String postId) {
        Set<CompanyOffer> result = companyOfferService.getByPostId(postId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Suggest change response entity.
     *
     * @param changed the changed
     * @return the response entity
     */
    @PatchMapping(value = "/suggestChange")
    public ResponseEntity<ChangedOffer> suggestChange(@Valid @RequestBody CompanyOffer changed) {
        ChangedOffer possibleChange = companyOfferService.suggestChange(changed);
        return new ResponseEntity<>(possibleChange, HttpStatus.OK);
    }

    /**
     * Gets changed offers.
     *
     * @param offerId the offer id
     * @return the changed offers
     */
    @GetMapping(value = "/getChanges")
    public ResponseEntity<Set<ChangedOffer>> getChangedOffers(
        @Valid @RequestParam("offerId") String offerId) {
        Set<ChangedOffer> result = companyOfferService.getChanges(offerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Accept changed offer response entity.
     *
     * @param changedId the changed id
     * @return the response entity
     */
    @PatchMapping(value = "/acceptChanges")
    public ResponseEntity<CompanyOffer> acceptChangedOffer(
        @Valid @RequestParam("changedId") String changedId) {
        CompanyOffer result = companyOfferService.acceptChange(changedId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Accept offer response entity.
     *
     * @param offerId the offer id
     * @return the response entity
     */
    @PatchMapping(value = "/acceptOffer")
    public ResponseEntity<CompanyOffer> acceptOffer(
        @Valid @RequestParam("offerId") String offerId) {
        CompanyOffer result = companyOfferService.acceptOffer(offerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Gets accepted offers.
     *
     * @param companyId the company id
     * @return the accepted offers
     */
    @GetMapping(value = "/getAcceptedOffers")
    public ResponseEntity<Set<CompanyOffer>> getAcceptedOffers(
            @Valid @RequestParam("companyId") String companyId
    ) {
        Set<CompanyOffer> returnSet = companyOfferService.getAcceptedOffers(companyId);
        return new ResponseEntity(returnSet, HttpStatus.OK);
    }

    /**
     * Create contract response entity.
     *
     * @param offerId   the offer id
     * @param startDate the start date
     * @param endDate   the end date
     * @return the response entity
     */
    @PostMapping(value = "/createContract")
    public ResponseEntity<Contract> createContract(
            @RequestParam @Valid String offerId,
            @RequestParam @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Contract returnContract = companyOfferService.createContract(
                offerId, startDate, endDate, new RestTemplate());
        return new ResponseEntity<>(returnContract, HttpStatus.CREATED);
    }

}
