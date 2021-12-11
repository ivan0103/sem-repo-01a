package nl.tudelft.sem.genericservicepost.repositories;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<GenericPost, Long> {
    GenericPost getPostById(Long id);
    Collection<GenericPost> getAllByExpertiseSetContaing(Expertise expertise);
    Collection<GenericPost> getAllByStudentOfferSetContaing(StudentOffer studentOffer);
}
