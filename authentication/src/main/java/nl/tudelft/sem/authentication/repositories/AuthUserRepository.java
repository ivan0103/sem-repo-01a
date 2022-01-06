package nl.tudelft.sem.authentication.repositories;

import nl.tudelft.sem.authentication.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
}
