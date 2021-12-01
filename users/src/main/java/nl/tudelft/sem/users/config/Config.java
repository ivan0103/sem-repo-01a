package nl.tudelft.sem.users.config;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.repositories.CompanyRepository;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.StudentRepository;
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
    CommandLineRunner userCommandLineRunner(CompanyRepository companyRepository,
                                            StudentRepository studentRepository,
                                            FeedbackRepository feedbackRepository) {

        return args -> {
            Feedback feedbackStudent = new Feedback(1L, "Chad", 10);
            Feedback feedbackCompany = new Feedback(2L, "Not Chad", 0);
            feedbackRepository.save(feedbackStudent);
            feedbackRepository.save(feedbackCompany);

            List<Feedback> feedbacksStudent = List.of(feedbackStudent);
            List<Feedback> feedbacksCompany = List.of(feedbackCompany);

            Student student = new Student("netID_Mate!", "name", 10.0f, feedbacksStudent);
            studentRepository.save(student);

            Company company = new Company("exploitEmployees", 10.0f, feedbacksCompany);
            companyRepository.save(company);
        };
    }
}
