package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
public class GenericPostService {

    @Autowired
    transient GenericPostRepository genericPostRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    /**
     * Create generic post and save in database.
     *
     * @param genericPost the post
     * @return the saved post
     */
    public GenericPost createGenericPost(GenericPost genericPost) {
        genericPost.setId(null);

        for (Expertise expertise : genericPost.getExpertiseSet()) {
            if (expertiseRepository.existsById(expertise.getExpertiseString())) {
                Expertise tmp = expertiseRepository.getExpertiseByExpertiseString(
                        expertise.getExpertiseString());
                tmp.getGenericPostSet().add(genericPost);
                expertiseRepository.save(tmp);
            } else {
                expertiseRepository.save(expertise);
            }
        }

        genericPost = genericPostRepository.save(genericPost);
        return genericPost;
    }

    /**
     * Edit generic post and save it.
     *
     * @param genericPost the generic Post
     * @return the generic Post
     * @throws GenericPostNotFoundException if id of the generic post was not found.
     */
    public GenericPost editGenericPost(GenericPost genericPost){
        if (genericPostRepository.existsById(genericPost.getId())){
            GenericPost edit = genericPostRepository.getGenericPostById(genericPost.getId());
            if (edit.getAuthor().equals(genericPost.getAuthor())){
                return genericPostRepository.save(genericPost);
            }
            else{
                throw new InvalidEditException();
            }
        }
        else throw new GenericPostNotFoundException();
    }
}
