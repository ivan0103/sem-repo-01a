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
                        .uri("http://localhost:8081"))

                // Route for student service post
                .route("offers", r -> r.path("/offers/**")
                        .uri("http://localhost:9090"))

                // Route for student service post
                .route("servicepost", r -> r.path("/servicepost/**")
                        .uri("http://localhost:9090"))

                // Route for generic service post
                .route("genericpost", r -> r.path("/genericpost/**")
                        .uri("http://localhost:8083"))

                // Route for contracts
                .route("contracts", r -> r.path("/contract/**")
                        .uri("http://localhost:8084"))

                // Route for authentication
                .route("authentication", r -> r.path("/authentication/**")
                        .uri("http://localhost:8085"))

                .build();
    }
}
