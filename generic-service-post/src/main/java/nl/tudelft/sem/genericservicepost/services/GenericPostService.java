package nl.tudelft.sem.genericservicepost.services;

import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.exceptions.StudentNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GenericPost editGenericPost(GenericPost genericPost) {
        if (genericPostRepository.existsById(genericPost.getId())) {
            GenericPost edit = genericPostRepository.getGenericPostById(genericPost.getId());
            if (edit.getAuthor().equals(genericPost.getAuthor())) {
                return genericPostRepository.save(genericPost);
            } else {
                throw new InvalidEditException();
            }
        } else {
            throw new GenericPostNotFoundException();
        }
    }

    /**
     * Issue #6
     * Companies Access to Student Info
     * Companies can view all the Students and their Data,
     * that have applied for one of their Job posts.
     *
     * @param genericPost the Post to collect the students from
     * @return a Set of all Students
     * @throws GenericPostNotFoundException if id of the generic post was not found / doesn't exist.
     */
    public Set<UserImpl> retrieveStudentsInPost(GenericPost genericPost) {
        if (genericPostRepository.existsById(genericPost.getId())) {
            return genericPost.getStudentSet();
        } else {
            throw new GenericPostNotFoundException();
        }
    }

    /**
     * Issue #16
     * Company can choose a student from StudentOffer
     * After viewing all their parameters in the above method
     * Method receives the selected student and assignes it to the posts paramter.
     *
     * @param student the selected student.
     * @param post    the post from which the student was selected and to which it will be assigned
     * @return the student.
     * @throws StudentNotFoundException if the Student Offer was not found in the generic post.
     * @throws GenericPostNotFoundException  if the post does not exist or not found.
     */

    public UserImpl setSelectedStudent(UserImpl student, GenericPost post) {
        if (genericPostRepository.existsById(post.getId())) {
            if (post.getStudentSet().contains(student)) {
                post.setSelectedStudent(student);
                return student;
            } else {
                throw new StudentNotFoundException();
            }
        } else {
            throw new GenericPostNotFoundException();
        }
    }
}
