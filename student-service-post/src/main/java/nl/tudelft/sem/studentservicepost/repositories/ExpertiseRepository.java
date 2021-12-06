package nl.tudelft.sem.studentservicepost.repositories;

import nl.tudelft.sem.studentservicepost.entities.Expertise;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, String> {

    Expertise getExpertiseByExpertiseString(String expertise);
}
