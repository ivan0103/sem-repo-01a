package nl.tudelft.sem.studentservicepost.repositories;

import nl.tudelft.sem.studentservicepost.entities.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, String> {

    Requirement getRequirementByRequirementString(String requirement);
}
