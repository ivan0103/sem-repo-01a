package nl.tudelft.sem.authentication.services;

import javassist.NotFoundException;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.entities.CommunicationEntity;
import nl.tudelft.sem.authentication.entities.User;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AuthUserService {

    private final transient AuthUserRepository authUserRepository;
    private final transient RestTemplate restTemplate;
    private final transient String usersApi = "http://USERS/users/";

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository, RestTemplate restTemplate) {
        this.authUserRepository = authUserRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Create a new account.
     *
     * @param communicationEntity entity that contains data necessary
*                                 for both users and authUsers
     * @return a new authenticated user or an exception
     */

    public AuthUser createAccount(CommunicationEntity communicationEntity) {
        if (authUserRepository.findById(communicationEntity.getNetID()).isPresent()) {
            throw new IllegalArgumentException("Net ID already exists!");
        }

        if (communicationEntity.getRole() == null) {
            throw new IllegalArgumentException("Role mustn't be null!");
        }

        try {
            User user = restTemplate.getForObject(
                    usersApi + communicationEntity.getNetID(), User.class
            );

            if (user != null) {
                throw new IllegalArgumentException("Net ID already exists!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        AuthUser authUser = new AuthUser(communicationEntity.getNetID(),
                                         communicationEntity.getPassword(),
                                         communicationEntity.getRole());

        restTemplate.execute(
                usersApi + "addUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getName() + "/"
                        + communicationEntity.getRole(),
                HttpMethod.POST, null, null
        );
        authUserRepository.save(authUser);

        return authUser;
    }

    //    /**
    //     * Retrieves an authenticated user.
    //     *
    //     * @param netID the net id of the user
    //     * @param password the password of the user
    //     * @return an authenticated user or null
    //     */
    //
    //    public AuthUser getAuthUser(String netID, String password) {
    //        if (authUserRepository.findById(netID).isEmpty()) {
    //            return null;
    //        }
    //
    //        AuthUser authUser = authUserRepository.findById(netID).get();
    //
    //        if (!authUser.getPassword().equals(password)) {
    //            return null;
    //        }
    //
    //        return authUser;
    //    }

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
