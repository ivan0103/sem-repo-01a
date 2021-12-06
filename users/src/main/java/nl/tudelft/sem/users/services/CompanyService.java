package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
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

        Company company = (Company) new UserFactory().createUser(netID, netID,0.0f,"company");
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
                + feedback.getRating()) / (toUser.getFeedbacks().size()));
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
     * @return an updated company
     */

    @Override
    public Company updateUser(String netID, String name) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (companyRepository.findById(netID).isPresent()
            && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        Company company = (Company) companyRepository.findById(netID).get();
        company.setName(name);
        companyRepository.save(company);
        return company;
    }

    /**
     * Allows students to edit their feedback.
     *
     * @param netID the id of the student
     * @param text the new text of the feedback
     * @param rating the new rating of the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return an edited feedback
     */

    @Override
    public Feedback editFeedback(String netID, String text, Integer rating,
                                 Long feedbackId, String toNetID) {

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

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            return null;
        }

        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        Company company = (Company) companyRepository.findById(netID).get();
        User receiver = companyRepository.findById(toNetID).get();
        List<Feedback> newFeedbacks = new ArrayList<>(receiver.getFeedbacks());
        newFeedbacks.remove(feedback);

        if (!feedback.getUser().equals(company)) {
            return null;
        }

        if (text != null) {
            feedback.setText(text);
        }

        if (rating != null) {
            feedback.setRating(rating);
        }

        feedbackRepository.save(feedback);
        newFeedbacks.add(feedback);
        receiver.setRating((receiver.getRating() * (receiver.getFeedbacks().size() - 1)
                + feedback.getRating()) / (receiver.getFeedbacks().size()));
        receiver.setFeedbacks(newFeedbacks);
        companyRepository.save(receiver);

        return feedback;
    }

    /**
     * Deletes a feedback.
     *
     * @param netID the id of the company that created the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return the deleted feedback
     */

    @Override
    public Feedback deleteFeedback(String netID, Long feedbackId, String toNetID) {
        if (companyRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (companyRepository.findById(toNetID).isEmpty()) {
            return null;
        }

        if (companyRepository.findById(netID).isPresent()
                && !(companyRepository.findById(netID).get() instanceof Company)) {

            return null;
        }

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            return null;
        }

        Company company = (Company) companyRepository.findById(netID).get();
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        User receiver = companyRepository.findById(toNetID).get();

        if (!feedback.getUser().equals(company) || !receiver.getFeedbacks().contains(feedback)) {
            return null;
        }

        if (receiver.getFeedbacks().size() - 1 <= 0) {
            receiver.setRating(0.0f);
        } else {
            receiver.setRating(((float) (receiver.getRating() * (receiver.getFeedbacks().size())
                    - ((float) feedback.getRating())))
                    / (((float) receiver.getFeedbacks().size() - 1)));
        }

        receiver.removeFeedback(feedback);
        feedbackRepository.delete(feedback);
        companyRepository.save(receiver);

        return feedback;
    }
}
