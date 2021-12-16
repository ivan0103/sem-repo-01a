package nl.tudelft.sem.authentication.config;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("nl.tudelft.sem.authentication")
@PropertySource("application.properties")
@EnableTransactionManagement
public class Config {

    @Bean("userCommandLineRunner")
    CommandLineRunner userCommandLineRunner(AuthUserRepository authUserRepository) {

        return args -> {
            AuthUser authUser = new AuthUser("aa", "vvv", "student");
            authUserRepository.save(authUser);
        };
    }
}
