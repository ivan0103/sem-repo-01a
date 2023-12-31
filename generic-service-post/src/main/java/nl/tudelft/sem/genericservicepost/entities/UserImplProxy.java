package nl.tudelft.sem.genericservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Transient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserImplProxy implements User {

    @Transient
    @JsonIgnore
    private final transient RestTemplate restTemplate;
    @Transient
    private final transient String netID;
    @Transient
    private transient User user;

    public UserImplProxy(String netID, RestTemplateBuilder restTemplateBuilder) {
        this.netID = netID;
        this.restTemplate = restTemplateBuilder.build();
    }

    private void retrieveRemoteUser() {
        //get user from remote location using netID field
        String url = "http://localhost:8080/users/{netID}";
        ResponseEntity<UserImpl> response = this.restTemplate.getForEntity(url,
                UserImpl.class, netID);
        if (response.getStatusCode() == HttpStatus.OK) {
            user = response.getBody();
        }
    }

    @Override
    public String getNetID() {
        if (user == null) {
            retrieveRemoteUser();
            if (user == null) {
                return null;
            }
        }
        return user.getNetID();
    }

    @Override
    public String getName() {
        if (user == null) {
            retrieveRemoteUser();
            if (user == null) {
                return null;
            }
        }
        return user.getName();
    }

    @Override
    public Float getRating() {
        if (user == null) {
            retrieveRemoteUser();
            if (user == null) {
                return null;
            }
        }
        return user.getRating();
    }

    @Override
    public List<Feedback> getFeedbacks() {
        if (user == null) {
            retrieveRemoteUser();
            if (user == null) {
                return null;
            }
        }
        return user.getFeedbacks();
    }
}
