package nl.tudelft.sem.genericservicepost.repositories;

import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentOfferRepository extends JpaRepository<StudentOffer, Long> {

    StudentOffer getById(Long id);

    Collection<StudentOffer> getAllByStudentId(String studentId);

    Set<StudentOffer> getAllByGenericPost(GenericPost post);

}
