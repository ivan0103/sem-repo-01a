package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
     * @param post the generic Post
     * @param postId the generic Post's id as String
     * @return the generic Post
     * @throws GenericPostNotFoundException if id of the generic post was not found.
     */
    public GenericPost editGenericPost(GenericPost post, String postId) {
        long id;
        try {
            id = Long.parseLong(postId);

            if (genericPostRepository.existsById(id)) {
                GenericPost toEdit = genericPostRepository.getGenericPostById(id);

                // this only checks that NetID is same
                if (toEdit.getAuthor().equals(post.getAuthor())) {
                    post.setId(toEdit.getId());
                    return genericPostRepository.save(post);
                } else {
                    throw new InvalidEditException();
                }
            } else {
                throw new GenericPostNotFoundException();
            }

        } catch (NumberFormatException e) {
            throw new GenericPostNotFoundException();
        }
    }

    public GenericPost getById(String postId) {
        long id;

        try {
            id = Long.parseLong(postId);
            if (genericPostRepository.existsById(id)) {
                return genericPostRepository.getGenericPostById(id);
            } else {
                throw new GenericPostNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new GenericPostNotFoundException();
        }
    }

    public Collection<GenericPost> getAll() {
        return genericPostRepository.findAll();
    }
}
