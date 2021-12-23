package nl.tudelft.sem.studentservicepost.repositories;

import java.util.Collection;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, String> {

    Expertise getExpertiseByExpertiseString(String expertise);

    Collection<Expertise> getAllByExpertiseStringContaining(String keyword);

    Collection<Expertise> getAllBySearchableStringContaining(String keyword);

}
