package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.repositories.CompanyRepository;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyService implements UserService<Company> {

    private final transient CompanyRepository companyRepository;
    private final transient FeedbackRepository feedbackRepository;

    /**
     * Constructor of CompanyRepository - It instantiates a new CompanyService object.
     *
     * @param companyRepository repository injected with data from the database
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          FeedbackRepository feedbackRepository) {

        this.companyRepository = companyRepository;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Getter for all the companies in the database.
     *
     * @return a list of companies contained in the database
     */

    @Override
    public List<Company> getUsers() {
        return companyRepository.findAll();
    }

    /**
     * Retrieves only one company based on the id.
     *
     * @param netID - the id of the company to be retrieved
     * @return the company
     */

    @Override
    public Company getOneUser(String netID) {
        if (companyRepository.findById(netID).isEmpty()) {
            //throw new IllegalStateException("Company nonexistent :/");
            return null;
        }

        return companyRepository.findById(netID).get();
    }

    /**
     * Creates a new company.
     *
     * @param netID the netID of the new company
     * @param name the name of the new company
     * @return a new company
     */

    @Override
    public Company addUser(String netID, String name) {
        if (companyRepository.findById(name).isPresent()) {
            return companyRepository.findById(name).get();
        }

        Company company = new Company(name, 0.0f, new ArrayList<>());
        companyRepository.save(company);
        return company;
    }

    /**
     * Creates a new feedback.
     *
     * @param netID id of the company
     * @param text text of the feedback
     * @param rating rating of the feedback
     * @return a new feedback
     */

    @Override
    public Feedback addFeedback(String netID, String text, Integer rating) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        Feedback feedback = new Feedback(text, rating);
        feedbackRepository.save(feedback);
        Company company = companyRepository.findById(netID).get();
        company.addFeedback(feedback);
        company.setRating((company.getRating() * (company.getFeedbacks().size() - 1)
                + ((float) feedback.getRating())) / ((float) company.getFeedbacks().size()));
        companyRepository.save(company);

        return feedback;
    }

    /**
     * Deletes the company with the corresponding netID.
     *
     * @param netID the id of the company
     * @return the company that was deleted
     */

    @Override
    public Company deleteUser(String netID) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        Company company = companyRepository.findById(netID).get();
        List<Feedback> feedbacks = new ArrayList<>(company.getFeedbacks());
        companyRepository.delete(company);
        feedbackRepository.deleteAll(feedbacks);

        return company;
    }

    /**
     * Updates information of company.
     *
     * @param netID the id of the company
     * @param name the new name of company
     * @param newNetID the new id of company (optional and not required)
     * @return an updated company
     */

    @Override
    public Company updateUser(String netID, String name, String newNetID) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        Company company = companyRepository.findById(netID).get();
        List<Feedback> feedbacks = new ArrayList<>();
        for (Feedback feedback : company.getFeedbacks()) {
            Feedback feed = new Feedback(feedback.getText(), feedback.getRating());
            feedbacks.add(feed);
        }
        deleteUser(netID);
        company.setName(name);
        company.setNetID(name);
        company.setFeedbacks(feedbacks);
        feedbackRepository.saveAll(feedbacks);
        companyRepository.save(company);
        return company;
    }
}
