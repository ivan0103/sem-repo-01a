package nl.tudelft.sem.genericservicepost.services;

import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.StudentNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.StudentOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentOfferService {

    @Autowired
    transient StudentOfferRepository studentOfferRepository;

    @Autowired
    transient GenericPostRepository genericPostRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    /**
     * Create student offer student offer.
     *
     * @param studentOffer the student offer
     * @param postId       the post id
     * @return the student offer
     * @throws GenericPostNotFoundException the generic post not found exception
     */
    public StudentOffer createStudentOffer(StudentOffer studentOffer, String postId)
        throws GenericPostNotFoundException {
        studentOffer.setId(null);
        try {
            long postIdL = Long.parseLong(postId);
            if (genericPostRepository.existsById(postIdL)) {
                GenericPost post = genericPostRepository.getGenericPostById(postIdL);
                post.getStudentOfferSet().add(studentOffer);
                studentOffer.setGenericPost(post);

                genericPostRepository.save(post);
                return studentOfferRepository.save(studentOffer);
            } else {
                throw new GenericPostNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new GenericPostNotFoundException();
        }
    }

    /**
     * Gets by generic post id.
     *
     * @param postId the post id
     * @return the by generic post id
     */
    public Set<StudentOffer> getByGenericPostId(String postId) {
        long postIdL;
        try {
            postIdL = Long.parseLong(postId);

            Set<StudentOffer> studentOffers;
            if (genericPostRepository.existsById(postIdL)) {
                GenericPost toCheck = genericPostRepository.getGenericPostById(postIdL);
                studentOffers = studentOfferRepository.getAllByGenericPost(toCheck);
            } else {
                throw new GenericPostNotFoundException();
            }
            return studentOffers;
        } catch (NumberFormatException e) {
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
