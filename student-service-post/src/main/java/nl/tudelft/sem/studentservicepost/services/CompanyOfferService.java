package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import org.springframework.stereotype.Service;


/**
 * The type Company offer service.
 */
@Service
public class CompanyOfferService {

    /**
     * Create offer company offer.
     *
     * @param companyOffer the company offer
     * @param postId       the post id
     * @return the company offer
     */
    public CompanyOffer createOffer(CompanyOffer companyOffer, String postId) {
        // Find post using PostId, add to companyOffer obj then save, do something if not found
        System.out.println("Offer: " + companyOffer + " for postID " + postId);
        return companyOffer;
    }
}
