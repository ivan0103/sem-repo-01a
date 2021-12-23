package nl.tudelft.sem.gateway.filters;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    /**
     * Intercept every request and verify the token.
     *
     * @param request - the request Http servlet.
     * @param response - the response Http servlet.
     * @param filterChain - the filter chain.
     * @throws ServletException - when the servlet encounters a difficulty.
     * @throws IOException - when token is not correct.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // If request is permitted by all then it shouldn't be authorized so let it go through.
        if (request.getServletPath().equals("/login/**")
                || request.getServletPath().equals("/refreshToken/**")
                || request.getServletPath().equals("/users/addUser/**")) {

            filterChain.doFilter(request, response);

        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            //Check if we have the correct header
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    // gets the token from header
                    String token = authorizationHeader.substring("Bearer ".length());

                    // The algorithm used for signature of token
                    Algorithm algorithm = Algorithm.HMAC256("Chimp".getBytes());

                    //Verify token
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    //get details of user
                    String username = decodedJWT.getSubject();
                    String role = decodedJWT.getClaim("role").asString();
                    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));

                    //Make a new token with the details of user
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    //Set the token in the security context folder to tell Spring security about it.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    //Move on in filter chain
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    //Indicate the error
                    response.setHeader("error", e.getMessage());
                    response.sendError(FORBIDDEN.value());

                }
            } else {
                //Move on in filter chain
                filterChain.doFilter(request, response);
            }
        }
    }
}
