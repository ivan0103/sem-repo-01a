package nl.tudelft.sem.genericservicepost.repositories;

import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserImpl, Long> {
    UserImpl getUserById(String netId);

    Boolean existsUserById(String netId);
}