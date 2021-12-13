package nl.tudelft.sem.genericservicepost.repositories;

import java.util.Collection;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<GenericPost, Long> {
    GenericPost getPostById(Long id);

    Collection<GenericPost> getAllByExpertiseSetContaining(Expertise expertise);

    Collection<GenericPost> getAllByStudentOfferSetContaining(StudentOffer studentOffer);
}
