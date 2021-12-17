package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.StudentOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class StudentOfferService {

    @Autowired
    transient StudentOfferRepository studentOfferRepository;

    @Autowired
    transient GenericPostRepository genericPostRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;

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
