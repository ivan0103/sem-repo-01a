package nl.tudelft.sem.genericservicepost.services;

import java.util.Collection;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditGenericPostService {

    @Autowired
    transient GenericPostRepository genericPostRepository;

    public boolean exists(Long id){
        return genericPostRepository.existsById(id);
    }

    public GenericPost save(GenericPost post){
        return genericPostRepository.save(post);
    }

    public GenericPost getPostById(Long id){
        return genericPostRepository.getGenericPostById(id);
    }

    /**
     * Edit generic post and save it.
     *
     * @param post the generic Post
     * @param postId the generic Post's id as String
     * @return the generic Post
     * @throws GenericPostNotFoundException if id of the generic post was not found / doesn't exist.
     */
    public GenericPost editGenericPost(GenericPost post, String postId) {
        long id;
        try {
            id = Long.parseLong(postId);

            if (exists(id)) {
                GenericPost toEdit = getPostById(id);

                // this only checks that NetID is same
                if (toEdit.getAuthor().equals(post.getAuthor())) {
                    post.setId(toEdit.getId());
                    return save(post);
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

}
