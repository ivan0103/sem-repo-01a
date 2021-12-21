package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
     * @throws GenericPostNotFoundException if id of the generic post was not found / doesn't exist.
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
        else{
            throw new GenericPostNotFoundException();
        }
    }

    /**
     * Issue #6
     * Companies Access to Student Info
     * Companies can view all the Students and their Data, that have applied for one of their Job posts
     *
     * @param genericPost the Post to collect the students from
     * @return a Set of all Students
     * @throws GenericPostNotFoundException if id of the generic post was not found / doesn't exist.
     */
    public Set<StudentOffer> retrieveStudentsInPost(GenericPost genericPost){
        Set<StudentOffer> result = new HashSet<>();
        if (genericPostRepository.existsById(genericPost.getId())){
            for (StudentOffer volunteer : genericPost.getStudentOfferSet()){
                if (Objects.equals(volunteer.getGenericPost().getId(), genericPost.getId())){
                    result.add(volunteer);
                }
            }
        }
        else {
            throw new GenericPostNotFoundException();
        }
        return result;
    }
}
