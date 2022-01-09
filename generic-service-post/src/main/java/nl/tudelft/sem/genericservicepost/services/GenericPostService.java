package nl.tudelft.sem.genericservicepost.services;

import java.awt.print.PrinterException;
import java.time.LocalTime;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.exceptions.CompanyLowScoreException;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.exceptions.StudentNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.UserNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.UserRepository;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenericPostService {

    @Autowired
    transient GenericPostRepository genericPostRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    @Autowired
    transient UserRepository userRepository;

    /**
     * Create generic post and save in database.
     *
     * @param genericPost the post
     * @return the saved post
     */
    public GenericPost createGenericPost(
            GenericPost genericPost) {
        // Constraints
        String netId = genericPost.getAuthor();
        String url = "http://localhost:8081users{" + netId + "}";
        RestTemplate restTemplate = new RestTemplate();
        UserImpl company = restTemplate.getForObject(url, UserImpl.class);

        if (company != null && company.getRating() >= 3F) {
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
        } else {
            throw new CompanyLowScoreException();
        }
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
     * Gets by id.
     *
     * @param postId the post id
     * @return the by id
     */
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

    /**
     * Gets by id.
     *
     * @param netId the user id
     * @return the user by id
     */
    public UserImpl getStudentById(String netId) {
        return userRepository.getUserById(netId);
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
    
    /**
     * Student signs up for a generic post.
     *
     * @param netId the student.
     * @param post    the post to which the student is signing up for.
     * @return the student.
     * @throws GenericPostNotFoundException  if the post does not exist or not found.
     * @throws UserNotFoundException if the student does not exist.
     */
    public UserImpl signUp(String netId, GenericPost post) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081users{" + netId + "}";
        UserImpl student = restTemplate.getForObject(url, UserImpl.class);
        if (student != null && userRepository.existsUserById(netId)) {
            if (genericPostRepository.existsById(post.getId())) {
                if (!post.getStudentSet().contains(student)) {
                    post.getStudentSet().add(student);
                    return student;
                }
                return student;
            } else {
                throw new GenericPostNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

}
