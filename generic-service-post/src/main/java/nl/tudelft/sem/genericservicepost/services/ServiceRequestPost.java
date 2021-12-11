package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Issue #15
 * The system can also allow the companies to post a request for services without targeting specific students
 */
@Service
public class ServiceRequestPost {
    @Autowired
    transient PostRepository postRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    /**
     * Create request post and save in database.
     *
     */
    public GenericPost createPost(GenericPost genericPost){
        genericPost.setId(null);
        for (Expertise expertise : genericPost.getExpertiseSet()){
            if (expertiseRepository.existsById(expertise.getExpertiseString())){
                Expertise exp = expertiseRepository.getExpertiseByExpertiseString(expertise.getExpertiseString());
                exp.getGenericPostSet().add(genericPost);
                expertiseRepository.save(exp);
            }
            else {
                expertiseRepository.save(expertise);
            }
        }
        GenericPost returned = postRepository.save(genericPost);
        System.out.println(returned);
        return returned;
    }
}
