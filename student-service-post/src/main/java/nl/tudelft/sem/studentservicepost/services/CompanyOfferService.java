package nl.tudelft.sem.studentservicepost.services;

import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The type Company offer service.
 */
@Service
public class CompanyOfferService {

    @Autowired
    transient CompanyOfferRepository companyOfferRepository;

    @Autowired
    transient PostRepository postRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    @Autowired
    transient RequirementRepository requirementRepository;

    /**
     * Create offer company offer.
     *
     * @param companyOffer the company offer
     * @param postId       the post id
     * @return the company offer
     */
    public CompanyOffer createOffer(CompanyOffer companyOffer, String postId) {
        // Find post using PostId, add to companyOffer obj then save, do something if not found

        companyOffer.setId(null);

        long postIdL = Long.parseLong(postId); // TODO catch NumberFormatException
        if (postRepository.existsById(postIdL)) {
            Post post = postRepository.getPostById(postIdL);
            post.getCompanyOfferSet().add(companyOffer);
            companyOffer.setPost(post);

            for (Expertise expertise : companyOffer.getExpertise()) {
                if (expertiseRepository.existsById(expertise.getExpertiseString())) {
                    Expertise tmp = expertiseRepository.getExpertiseByExpertiseString(
                        expertise.getExpertiseString());
                    tmp.getOfferSet().add(companyOffer);
                    expertiseRepository.save(tmp);
                } else {
                    expertiseRepository.save(expertise);
                }
            }

            for (Requirement requirement : companyOffer.getRequirementsSet()) {
                if (requirementRepository.existsById(requirement.getRequirementString())) {
                    Requirement tmp = requirementRepository.getRequirementByRequirementString(
                        requirement.getRequirementString());
                    tmp.getCompanyOfferSet().add(companyOffer);
                    requirementRepository.save(tmp);
                } else {
                    requirementRepository.save(requirement);
                }
            }
            postRepository.save(post);
            return companyOfferRepository.save(companyOffer);
        } else {
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
        long postIdL = Long.parseLong(postId); // TODO catch NumberFormatException
        Set<CompanyOffer> result;
        if (postRepository.existsById(postIdL)) {
            Post toCheck = postRepository.getPostById(postIdL);
            result = companyOfferRepository.getAllByPost(toCheck);
        } else {
            throw new PostNotFoundException();
        }
        return result;
    }
}
