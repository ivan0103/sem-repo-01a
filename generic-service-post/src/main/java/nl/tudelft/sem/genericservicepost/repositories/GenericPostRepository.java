package nl.tudelft.sem.genericservicepost.repositories;

import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericPostRepository extends JpaRepository<GenericPost, Long> {

    GenericPost getGenericPostById(Long id);
}
