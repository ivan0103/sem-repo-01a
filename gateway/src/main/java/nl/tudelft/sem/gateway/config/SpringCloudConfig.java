package nl.tudelft.sem.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringCloudConfig {

    /**
     * Route locator that helps the gateway redirect the client.
     *
     * @param builder builds routes
     * @return routes to microservices
     */

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                //Route for users
                .route("users", r -> r.path("/users/**")
                        .uri("lb://USERS"))

                // Route for student service post
                .route("offers", r -> r.path("/offers/**")
                        .uri("lb://STUDENT-SERVICE-POST"))

                // Route for student service post
                .route("servicepost", r -> r.path("/servicepost/**")
                        .uri("lb://STUDENT-SERVICE-POST"))

                // Route for generic service post
                .route("genericpost", r -> r.path("/genericpost/**")
                        .uri("lb://GENERIC-SERVICE-POST"))

                // Route for contracts
                .route("contracts", r -> r.path("/contract/**")
                        .uri("lb://CONTRACTS"))

                // Route for authentication
                .route("login", r -> r.path("/login/**")
                        .uri("lb://AUTHENTICATION"))

                .build();
    }
}
