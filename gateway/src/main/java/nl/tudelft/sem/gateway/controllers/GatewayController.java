package nl.tudelft.sem.gateway.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class GatewayController {

    private final transient GatewayService gatewayService;


    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping
    public String greetAuthenticatedUsers() {
        return "HOORAY! You have successfully logged in!";
    }

    @GetMapping("/isAuthenticated/{netID}/{password}")
    public Boolean isAuthenticated(@PathVariable(value = "netID") String netID,
                                   @PathVariable(value = "password") String password) {
        return gatewayService.isAuthenticated(netID, password);
    }

    /**
     * Endpoint for refreshing expired JWT tokens.
     *
     * @param request - the request Http servlet
     * @param response - the response Http servlet
     * @throws IOException When the refresh token is missing.
     */
    @GetMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        //Check if we have the correct header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // gets the token from header and set as refresh token
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                // The algorithm used for signature of token
                Algorithm algorithm = Algorithm.HMAC256("Chimp".getBytes());

                //Verify token
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                //Make a new access token with user information
                String username = decodedJWT.getSubject();
                AuthUser user = gatewayService.getAuthUserByUsername(username);

                String accessToken = JWT.create()
                        .withSubject(user.getNetID())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", user.getRole())
                        .sign(algorithm);


                //attach tokens to response
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                //inform user of error
                response.setHeader("error", e.getMessage());
                response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
