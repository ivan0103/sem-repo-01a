package nl.tudelft.sem.authentication.services;

import javassist.NotFoundException;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthUserService {

    private final transient AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    /**
     * Create a new authUser.
     *
     * @param netID the id of the new authUser
     * @param password the password of the new authUser
     * @param role the role of the authUser
     * @return a new authUser
     */

    public AuthUser addAuthUser(String netID, String password, String role)
            throws IllegalArgumentException {
        if (netID == null) {
            throw new IllegalArgumentException("NetID cannot be null!");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null!");
        }
        if (!role.equalsIgnoreCase("student")
            && !role.equalsIgnoreCase("company")) {

            throw new IllegalArgumentException("Invalid role");
        }
        if (authUserRepository.findById(netID).isPresent()) {
            throw new IllegalArgumentException("User with this netID already exists!");
        }

        AuthUser authUser = new AuthUser(netID, password, role);
        authUserRepository.save(authUser);
        return authUser;
    }

    /**
     * Method to check whether the authenticated user exists.
     *
     * @param authUser entity that stores a netID and a password
     * @return true - if the authUser exists in the microservice database
     *         false - otherwise
     */

    public Boolean isAuthenticated(AuthUser authUser) {
        if (authUserRepository.findById(authUser.getNetID()).isEmpty()) {
            return false;
        }

        if (authUser.getRole() == null) {
            return false;
        }

        AuthUser authUserRepo = authUserRepository.findById(authUser.getNetID()).get();

        return authUserRepo.getPassword().equals(authUser.getPassword())
                && authUserRepo.getRole().equals(authUser.getRole());
    }

    /**
     * Get the account of a user based on their netID.
     *
     * @param netID the netID of the user
     * @return the corresponding authUser
     * @throws NotFoundException if the netID does not exist
     */

    public AuthUser getAccount(String netID) throws NotFoundException {
        if (authUserRepository.findById(netID).isEmpty()) {
            throw new NotFoundException("User with this netID does not exist!");
        }
        return authUserRepository.findById(netID).get();
    }
}
