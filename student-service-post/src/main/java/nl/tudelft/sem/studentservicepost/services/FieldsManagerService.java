package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldsManagerService {


    @Autowired
    private transient ExpertiseRepository expertiseRepository;

    @Autowired
    private transient RequirementRepository requirementRepository;

    @Autowired
    private transient PostRepository postRepository;


    void updateExpertise(CompanyOffer companyOffer) {
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
    }

    void updateRequirement(CompanyOffer companyOffer) {

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
    }

    boolean updatePost(CompanyOffer companyOffer, String postId) {
        long postIdL = Long.parseLong(postId);
        if (postRepository.existsById(postIdL)) {
            Post post = postRepository.getPostById(postIdL);
            post.getCompanyOfferSet().add(companyOffer);
            companyOffer.setPost(post);
            postRepository.save(post);
            return true;
        } else {
            return false;
        }

    }

}
