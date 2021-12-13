package nl.tudelft.sem.genericservicepost.repositories;


import nl.tudelft.sem.genericservicepost.entities.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, String> {

    Expertise getExpertiseByExpertiseString(String expertise);
}
