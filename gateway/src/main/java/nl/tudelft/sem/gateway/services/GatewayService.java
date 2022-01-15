package nl.tudelft.sem.gateway.services;

import java.util.List;
import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.entities.CommunicationEntity;
import nl.tudelft.sem.gateway.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;


@Service
public class GatewayService implements ReactiveUserDetailsService {


    private final transient RestTemplate restTemplate;
    private final transient String urlAuth = "http://AUTHENTICATION/authentication/";
    private final transient String usersApi = "http://USERS/users/";

    @Autowired
    public GatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<UserDetails> findByUsername(String netID) {
        try {
            AuthUser authUser = restTemplate.getForObject(
                    urlAuth + "/ADMIN?netID=" + netID, AuthUser.class
            );

            if (authUser == null) {
                throw new IllegalArgumentException("NetID does not exist!");
            }

            return Mono.just(
                    new org.springframework.security.core.userdetails.User(
                            authUser.getNetID(),
                    new BCryptPasswordEncoder().encode(
                            authUser.getPassword()),
                    List.of(new SimpleGrantedAuthority(authUser.getRole()))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Create a new account.
     *
     * @param communicationEntity entity that contains data necessary
     *                                 for both users and authUsers
     * @return a new authenticated user or an exception
     * @throws IllegalArgumentException if one of the arguments is invalid
     */

    public AuthUser createAccount(CommunicationEntity communicationEntity)
            throws IllegalArgumentException {

        if (communicationEntity.getRole() == null) {
            throw new IllegalArgumentException("Role must not be null!");
        }

        User user = getUser(communicationEntity.getNetID());

        if (user != null) {
            throw new IllegalArgumentException("Net ID already exists!");
        }

        AuthUser authenticatedUser = getAuthenticatedUser(communicationEntity.getNetID());

        if (authenticatedUser != null) {
            throw new IllegalArgumentException("NetID does not exist!");
        }

        AuthUser authUser = new AuthUser(communicationEntity.getNetID(),
                communicationEntity.getPassword(),
                communicationEntity.getRole());

        executePOSTS(communicationEntity);

        return authUser;
    }

    private User getUser(String netID) {
        try {
            return restTemplate.getForObject(
                    usersApi + netID, User.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private AuthUser getAuthenticatedUser(String netID) {
        try {
            return restTemplate.getForObject(
                    urlAuth + "/ADMIN?netID=" + netID, AuthUser.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executePOSTS(CommunicationEntity communicationEntity) {
        restTemplate.execute(
                urlAuth + "addAuthUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getPassword() + "/"
                        + communicationEntity.getRole(),
                HttpMethod.POST, null, null
        );

        restTemplate.execute(
                usersApi + "addUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getName() + "/"
                        + communicationEntity.getRole(),
                HttpMethod.POST, null, null
        );
    }
}
