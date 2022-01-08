package nl.tudelft.sem.users;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class Users {

    public static void main(String[] args) {
        SpringApplication.run(Users.class, args);
    }

    @Bean("userCommandLineRunner")
    CommandLineRunner userCommandLineRunner(UserRepository userRepository,
                                            FeedbackRepository feedbackRepository) {

        return args -> {
            User student = new UserFactory().createUser("netID_Mate!",
                    "name", 10.0f, "student");

            userRepository.save(student);
            Feedback feedbackFromStudent = new Feedback("Chad", 10, student);
            List<Feedback> feedbacksCompany = List.of(feedbackFromStudent);
            User company = new UserFactory().createUser("exploitEmployees",
                    "exploitEmployees", 10.0f, feedbacksCompany, "company");
            userRepository.save(company);
        };
    }

}
