package nl.tudelft.sem.studentservicepost.repositories;

import java.util.Collection;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyRepository extends JpaRepository<Competency, String> {

    Competency getCompetencyByCompetencyString(String competency);

    Collection<Competency> getAllByCompetencyStringContaining(String keyword);
}
