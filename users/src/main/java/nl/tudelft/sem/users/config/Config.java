package nl.tudelft.sem.users.config;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
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
            Student student = new Student("netID_Mate!", "name", 10.0f);
            userRepository.save(student);
            Feedback feedbackFromStudent = new Feedback("Chad", 10, student);
            List<Feedback> feedbacksCompany = List.of(feedbackFromStudent);
            Company company = new Company("exploitEmployees", 10.0f, feedbacksCompany);
            userRepository.save(company);
        };
    }
}
