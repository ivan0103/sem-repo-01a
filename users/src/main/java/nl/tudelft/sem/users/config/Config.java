package nl.tudelft.sem.users.config;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Role;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("nl.tudelft.sem.users")
@PropertySource("application.properties")
@EnableTransactionManagement
public class Config {

    @Bean("userCommandLineRunner")
    CommandLineRunner userCommandLineRunner(UserRepository userRepository,
                                            FeedbackRepository feedbackRepository) {

        return args -> {
            Feedback feedback = new Feedback(1L, "Chad", 10);
            List<Feedback> feedbacks = List.of(feedback);
            User user = new User("netID_Mate!", "name", 10.0f, feedbacks, Role.student);

            feedbackRepository.save(feedback);
            userRepository.save(user);
        };
    }
}
