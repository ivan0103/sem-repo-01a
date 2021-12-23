package nl.tudelft.sem.gateway.communication;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import nl.tudelft.sem.gateway.entities.AuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserServiceCommunication implements UserDetailsService {

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final Gson gson = new Gson();


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
    public static AuthUser getAuthUserByUsername(String username) throws UsernameNotFoundException {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/users/{" + username + "}")).build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User not found in the database");
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return gson.fromJson(response.body(), AuthUser.class);
    }
}
