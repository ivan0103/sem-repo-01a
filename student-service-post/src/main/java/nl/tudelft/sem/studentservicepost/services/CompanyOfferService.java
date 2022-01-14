package nl.tudelft.sem.studentservicepost.services;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.Valid;
import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Contract;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.OfferNotAcceptedException;
import nl.tudelft.sem.studentservicepost.exceptions.OfferNotFoundException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.ChangedOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * The type Company offer service.
 */
@Service
public class CompanyOfferService {

    /**
     * The Company offer repository.
     */
    @Autowired
    transient CompanyOfferRepository companyOfferRepository;

    /**
     * The Post repository.
     */
    @Autowired
    transient PostRepository postRepository;

    @Autowired
    private transient FieldsManagerService fieldsManagerService;

    /**
     * The Changed offer repository.
     */
    @Autowired
    transient ChangedOfferRepository changedOfferRepository;

    /**
     * Create offer company offer.
     *
     * @param companyOffer the company offer
     * @param postId       the post id
     * @return the company offer
     * @throws PostNotFoundException the post not found exception
     */
    public CompanyOffer createOffer(CompanyOffer companyOffer, String postId)
        throws PostNotFoundException {
        // Find post using PostId, add to companyOffer obj then save, do something if not found

        companyOffer.setId(null);
        try {
            if (fieldsManagerService.updatePost(companyOffer, postId)) {
                fieldsManagerService.updateExpertise(companyOffer);
                fieldsManagerService.updateRequirement(companyOffer);
                return companyOfferRepository.save(companyOffer);
            } else {
                throw new PostNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new PostNotFoundException();
        }
    }

    /**
     * Gets all offers by post id.
     *
     * @param postId the post id
     * @return set of all offers
     */
    public Set<CompanyOffer> getByPostId(String postId) {
        long postIdL;
        try {
            postIdL = Long.parseLong(postId);

            Set<CompanyOffer> result;
            if (postRepository.existsById(postIdL)) {
                Post toCheck = postRepository.getPostById(postIdL);
                result = companyOfferRepository.getAllByPost(toCheck);
            } else {
                throw new PostNotFoundException();
            }
            return result;
        } catch (NumberFormatException e) {
            throw new PostNotFoundException();
        }


    }

    /**
     * Suggest change to an offer.
     *
     * @param changed the offer to be changed
     * @return the changed offer
     */
    public ChangedOffer suggestChange(CompanyOffer changed) {
        long offerId = changed.getId();

        ChangedOffer result;

        if (companyOfferRepository.existsById(offerId)) {
            result = new ChangedOffer(changed);
            result.setId(null);
            companyOfferRepository.save(result);
        } else {
            throw new OfferNotFoundException();
        }

        return result;
    }

    /**
     * Gets changes.
     *
     * @param offerId the offer id
     * @return the changes
     */
    public Set<ChangedOffer> getChanges(String offerId) {

        long id;
        try {
            id = Long.parseLong(offerId);

            if (companyOfferRepository.existsById(id)) {
                CompanyOffer tmp = companyOfferRepository.getById(id);
                return tmp.getChangedOffers();
            } else {
                throw new OfferNotFoundException();
            }

        } catch (NumberFormatException e) {
            throw new OfferNotFoundException();
        }
    }

    /**
     * Accept change company offer.
     *
     * @param changedId the changed id
     * @return the company offer
     */
    public CompanyOffer acceptChange(String changedId) {

        long id;
        try {
            id = Long.parseLong(changedId);

            if (changedOfferRepository.existsById(id)) {
                ChangedOffer tmp = changedOfferRepository.getById(id);
                CompanyOffer toChange = tmp.getParent();

                toChange.setPricePerHour(tmp.getPricePerHour());
                toChange.setTotalHours(tmp.getTotalHours());
                toChange.setWeeklyHours(tmp.getWeeklyHours());

                //other stuff should never be changed

                toChange.getChangedOffers().remove(tmp);

                // deleting changed offer? it's complicated, so let's keep it for now
                //  changedOfferRepository.deleteChangedOfferById(id);
                return companyOfferRepository.save(toChange);
            } else {
                throw new OfferNotFoundException();
            }

        } catch (NumberFormatException e) {
            throw new OfferNotFoundException();
        }
    }

    /**
     * Accepts a company offer.
     *
     * @param offerId the offer id
     * @return the accepted company offer
     */
    public CompanyOffer acceptOffer(String offerId) {
        long id;
        try {
            id = Long.parseLong(offerId);

            if (companyOfferRepository.existsById(id)) {
                CompanyOffer offer = companyOfferRepository.getById(id);
                offer.setAccepted(true);
                return companyOfferRepository.save(offer);

            } else {
                throw new OfferNotFoundException();
            }

        } catch (NumberFormatException e) {
            throw new OfferNotFoundException();
        }
    }

    /**
     * Gets accepted offers.
     *
     * @param companyId the company id
     * @return the accepted offers
     */
    public Set<CompanyOffer> getAcceptedOffers(String companyId) {
        if (companyOfferRepository.existsByCompanyId(companyId)) {
            Set<CompanyOffer> companyOffer = companyOfferRepository
                    .getAllByCompanyIdAndAcceptedIsTrue(companyId);
            return companyOffer;
        } else {
            throw new OfferNotFoundException();
        }
    }


    /**
     * Create contract contract.
     *
     * @param offerId   the offer id
     * @param startDate the start date
     * @param endDate   the end date
     * @return the contract
     */
    public Contract createContract(
            String offerId,
            @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            RestTemplate restTemplate
    ) {
        long id;
        try {
            id = Long.parseLong(offerId);

            if (companyOfferRepository.existsById(id)) {
                CompanyOffer offer = companyOfferRepository.getById(id);
                if (!offer.isAccepted()) {
                    throw new OfferNotAcceptedException();
                }
                Contract contract = Contract.buildFromOffer(offer, startDate, endDate);
                String url = "http://localhost:7070/contract/create";
                return (Contract) makeRequest(url, contract, restTemplate).getBody();
            } else {
                throw new OfferNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new OfferNotFoundException();
        }
    }

    private ResponseEntity<Object> makeRequest(
            String url,
            Object object,
            RestTemplate restTemplate
    ) {
        HttpEntity<Object> request = new HttpEntity<>(object);
        try {
            Class cls = Class.forName(object.getClass().getName());
            return restTemplate.postForEntity(url, request, cls);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found. Bad request");
        }
    }

}
