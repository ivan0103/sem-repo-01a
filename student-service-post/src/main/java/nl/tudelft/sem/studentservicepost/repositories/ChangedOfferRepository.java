package nl.tudelft.sem.studentservicepost.repositories;

import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangedOfferRepository extends JpaRepository<ChangedOffer, Long> {

    ChangedOffer getById(Long id);

    void deleteChangedOfferById(Long id);
}
