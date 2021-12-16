package nl.tudelft.sem.authentication.services;

import java.util.Optional;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.entities.Student;
import nl.tudelft.sem.authentication.entities.User;
import nl.tudelft.sem.authentication.entities.UserFactory;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AuthUserService {

    private final transient AuthUserRepository authUserRepository;
    private final transient RestTemplate restTemplate;
    private final transient String usersApi = "http://localhost:8081/users/";

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository, RestTemplate restTemplate) {
        this.authUserRepository = authUserRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Create a new account.
     *
     * @param netID the net id of the user
     * @param name the name of the user
     * @param password the password of the user
     * @param role the role of the user
     * @return a new authenticated user or an exception
     */

    public AuthUser createAccount(String netID, String name, String password, String role) {
        if (authUserRepository.findById(netID).isPresent()) {
            throw new IllegalArgumentException("Net ID already exists!");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role mustn't be null!");
        }

        AuthUser authUser = new AuthUser(netID, password, role);

        restTemplate.execute(
                usersApi + netID + "/" + name + "/" + role,
                HttpMethod.POST, null, null
        );

        authUserRepository.save(authUser);
        return authUser;
    }

    /**
     * Retrieves an authenticated user.
     *
     * @param netID the net id of the user
     * @param password the password of the user
     * @return an authenticated user or null
     */

    public AuthUser getAuthUser(String netID, String password) {
        if (authUserRepository.findById(netID).isEmpty()) {
            return null;
        }

        AuthUser authUser = authUserRepository.findById(netID).get();

        if (authUser.getPassword().equals(password)) {
            return null;
        }

        return authUser;
    }
}
