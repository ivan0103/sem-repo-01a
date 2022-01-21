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
}
