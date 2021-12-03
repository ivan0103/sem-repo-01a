package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyService implements UserService<Company> {

    private final transient UserRepository companyRepository;
    private final transient FeedbackRepository feedbackRepository;

    /**
     * Constructor of CompanyRepository - It instantiates a new CompanyService object.
     *
     * @param companyRepository repository injected with data from the database
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public CompanyService(UserRepository companyRepository, FeedbackRepository feedbackRepository) {

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
        List<Company> companies = new ArrayList<>();

        for (User user : companyRepository.findAll()) {
            if (user instanceof Company) {
                companies.add((Company) user);
            }
        }

        return companies;
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

        if (companyRepository.findById(netID).isPresent()
            && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        return (Company) companyRepository.findById(netID).get();
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
        if (companyRepository.findById(netID).isPresent()
            && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        if (companyRepository.findById(netID).isPresent()
            && (companyRepository.findById(netID).get() instanceof Company)) {

            return (Company) companyRepository.findById(netID).get();
        }

        Company company = new Company(netID, 0.0f, new ArrayList<>());
        companyRepository.save(company);
        return company;
    }

    /**
     * Creates a new feedback.
     *
     * @param netID id of the company
     * @param text text of the feedback
     * @param rating rating of the feedback
     * @param toNetID the netID of the user that receives the feedback
     * @return a new feedback
     */

    @Override
    public Feedback addFeedback(String netID, String text, Integer rating, String toNetID) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (companyRepository.findById(netID).isPresent()
                && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        if (companyRepository.findById(toNetID).isEmpty()) {
            return null;
        }

        Company company = (Company) companyRepository.findById(netID).get();
        Feedback feedback = new Feedback(text, rating, company);
        User toUser = companyRepository.findById(toNetID).get();
        toUser.addFeedback(feedback);
        toUser.setRating((toUser.getRating() * (toUser.getFeedbacks().size() - 1)
                + ((float) feedback.getRating())) / ((float) toUser.getFeedbacks().size()));
        companyRepository.save(toUser);

        return feedbackRepository.findTopByOrderByIdDesc();
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

        if (companyRepository.findById(netID).isPresent()
            && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        Company company = (Company) companyRepository.findById(netID).get();
        List<User> users = companyRepository.findAll();
        List<Feedback> removals = new ArrayList<>();

        for (User user : users) {
            List<Feedback> keeps = new ArrayList<>();

            for (Feedback feedback : user.getFeedbacks()) {
                if (feedback.getUser().equals(company)) {
                    removals.add(feedback);
                } else {
                    keeps.add(feedback);
                }
            }

            user.setFeedbacks(keeps);
            companyRepository.save(user);
        }

        feedbackRepository.deleteAll(removals);
        companyRepository.delete(company);
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

        if (companyRepository.findById(netID).isPresent()
            && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        Company company = (Company) companyRepository.findById(netID).get();
        deleteUser(netID);
        company.setName(name);
        company.setNetID(name);
        companyRepository.save(company);
        return company;
    }
}
