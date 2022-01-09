package nl.tudelft.sem.authentication;

import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Authentication {

    public static void main(String[] args) {
        SpringApplication.run(Authentication.class, args);
    }

    @Bean("userCommandLineRunner")
    CommandLineRunner userCommandLineRunner(AuthUserRepository authUserRepository) {

        return args -> {
            AuthUser authUser = new AuthUser("aa", "vvv", "student");
            authUserRepository.save(authUser);
        };
    }

}
