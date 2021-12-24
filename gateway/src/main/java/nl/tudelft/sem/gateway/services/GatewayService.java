package nl.tudelft.sem.gateway.services;

import java.util.ArrayList;
import nl.tudelft.sem.gateway.entities.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GatewayService implements UserDetailsService {

    private final transient RestTemplate restTemplate;
    private final transient String urlAuth = "http://localhost:8085/authentication/";

    @Autowired
    public GatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * Checks whether the user is truly authenticated.
     *
     * @param netID the net id
     * @param password the password
     * @return true - if the user is indeed authenticated
     *         false -  otherwise
     */
    /*public Boolean isAuthenticated(String netID, String password) {
        AuthUser authUser = restTemplate.getForObject(
               urlAuth + netID + "/" + password,
               AuthUser.class
        );

        return authUser != null;
    }*/

    /**
     * This method loads user's details from the user microservice to be used by
     * spring security for authentication.
     *
     * @param username - username(or NetID) of a user.
     * @return the details of that user.
     * @throws UsernameNotFoundException when the username searched for doesn't have an account.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser user = getAuthUserByUsername(username);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getNetID(),
                user.getPassword(),
                authorities
        );

    }


    /**
     * Gets the user instance corresponding to a username from users repository
     * in user microservice.
     *
     * @param username - username(or NetID) of a user.
     * @return the instance of the user.
     * @throws UsernameNotFoundException when the username searched for doesn't have an account.
     */
    public AuthUser getAuthUserByUsername(String username) throws UsernameNotFoundException {

        try {
            AuthUser user = restTemplate.getForObject("http://users/" + "{" + username + "}", AuthUser.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User not found in the database");
        }
    }
}
