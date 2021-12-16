package nl.tudelft.sem.gateway.services;

import nl.tudelft.sem.gateway.entities.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GatewayService {

    private final transient RestTemplate restTemplate;
    private final transient String urlAuth = "http://localhost:8085/authentication/";

    @Autowired
    public GatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Checks whether the user is truly authenticated.
     *
     * @param netID the net id
     * @param password the password
     * @return true - if the user is indeed authenticated
     *         false -  otherwise
     */

    public Boolean isAuthenticated(String netID, String password) {
        AuthUser authUser = restTemplate.getForObject(
               urlAuth + netID + "/" + password,
               AuthUser.class
        );

        return authUser != null;
    }
}
