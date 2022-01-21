package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final transient UserRepository userRepository;
    private final transient FeedbackRepository feedbackRepository;
    private final transient String userNotFound = "User with this netID does not exist";
    private final transient String feedbackNotFound = "Feedback with this id does not exist";

    /**
     * Constructor of UserService - It instantiates a new UserService object.
     *
     * @param userRepository repository injected with data from the database
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public UserService(UserRepository userRepository, FeedbackRepository feedbackRepository) {

        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Getter for all the users in the database.
     *
     * @return a list of users contained in the database
     */

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves only one user based on the id.
     *
     * @param netID - the id of the user to be retrieved
     * @return the user
     */

    public User getOneUser(String netID) throws NullPointerException {
        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        return userRepository.findById(netID).get();
    }

    /**
     * Creates a new user.
     *
     * @param netID the netID of the new user
     * @param name the name of the new user
     * @param role the role of the user
     * @return a new user
     * @throws IllegalArgumentException if we try to add the same user twice
     */

    public User addUser(String netID, String name, String role) throws IllegalArgumentException {
        if (userRepository.findById(netID).isPresent()) {
            throw new IllegalArgumentException("User with this netID already exists!");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }

        User user = new UserFactory().createUser(netID, name, 0.0f, role);
        userRepository.save(user);
        return user;
    }

    /**
     * Creates a new feedback.
     *
     * @param netID id of the user
     * @param text text of the feedback
     * @param rating rating of the feedback
     * @param toNetID the netID of the user that receives the feedback
     * @return a new feedback
     */

    public Feedback addFeedback(String netID, String text, Integer rating, String toNetID)
                                throws NullPointerException {

        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        if (userRepository.findById(toNetID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        User user = userRepository.findById(netID).get();
        Feedback feedback = new Feedback(text, rating, user);
        feedbackRepository.save(feedback);
        Feedback feedbackFromRepo = feedbackRepository.findTopByOrderByIdDesc();
        User toUser = userRepository.findById(toNetID).get();
        List<Feedback> toUserFeedbacks = new ArrayList<>(toUser.getFeedbacks());
        toUserFeedbacks.add(feedbackFromRepo);
        toUser.setFeedbacks(toUserFeedbacks);
        float newRating = 0.0f;
        for (Feedback feedback1 : toUserFeedbacks) {
            newRating += feedback1.getRating();
        }
        newRating /= toUserFeedbacks.size();
        toUser.setRating(newRating);
        userRepository.save(toUser);

        return feedbackFromRepo;
    }

    /**
     * Deletes the user with the corresponding netID.
     *
     * @param netID the id of the user
     * @return the user that was deleted
     */

    public User deleteUser(String netID) throws NullPointerException {
        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        User userToBeDeleted = userRepository.findById(netID).get();
        List<User> users = userRepository.findAll();
        List<Feedback> removals = new ArrayList<>();

        for (User user : users) {
            List<Feedback> keeps = new ArrayList<>();

            for (Feedback feedback : user.getFeedbacks()) {
                if (feedback.getUser().equals(userToBeDeleted)) {
                    removals.add(feedback);
                } else {
                    keeps.add(feedback);
                }
            }

            user.setFeedbacks(keeps);
            userRepository.save(user);
        }

        feedbackRepository.deleteAll(removals);
        userRepository.delete(userToBeDeleted);
        return userToBeDeleted;
    }

    /**
     * Updates information of user.
     *
     * @param netID the id of the user
     * @param name the new name of user
     * @return an updated user
     */

    public User updateUser(String netID, String name) throws NullPointerException {
        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        User user = userRepository.findById(netID).get();
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    /**
     * Allows users to edit their feedback.
     *
     * @param netID the id of the user
     * @param text the new text of the feedback
     * @param rating the new rating of the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return an edited feedback
     */

    public Feedback editFeedback(String netID, String text,
                                 Integer rating, Long feedbackId, String toNetID)
                                 throws NullPointerException {

        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        if (userRepository.findById(toNetID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            throw new NullPointerException(feedbackNotFound);
        }

        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        User user = userRepository.findById(netID).get();
        User receiver = userRepository.findById(toNetID).get();

        if (!feedback.getUser().equals(user) || !receiver.getFeedbacks().contains(feedback)) {
            return null;
        }

        List<Feedback> receiverFeedbacks = new ArrayList<>(receiver.getFeedbacks());
        receiverFeedbacks.remove(feedback);

        if (text != null) {
            feedback.setText(text);
        }

        if (rating != null) {
            feedback.setRating(rating);
        }

        feedbackRepository.save(feedback);
        receiverFeedbacks.add(feedback);
        receiver.setFeedbacks(receiverFeedbacks);
        float newRating = 0.0f;
        for (Feedback feedback1 : receiverFeedbacks) {
            newRating += feedback1.getRating();
        }
        newRating /= receiverFeedbacks.size();
        receiver.setRating(newRating);
        userRepository.save(receiver);

        return feedback;
    }

    /**
     * Deletes a feedback.
     *
     * @param netID the id of the user that created the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return the deleted feedback
     */

    public Feedback deleteFeedback(String netID, Long feedbackId, String toNetID)
            throws NullPointerException {

        if (userRepository.findById(netID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        if (userRepository.findById(toNetID).isEmpty()) {
            throw new NullPointerException(userNotFound);
        }

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            throw new NullPointerException(feedbackNotFound);
        }

        User user = userRepository.findById(netID).get();
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        User receiver = userRepository.findById(toNetID).get();

        if (!feedback.getUser().equals(user) || !receiver.getFeedbacks().contains(feedback)) {
            return null;
        }

        List<Feedback> receiverFeedbacks = new ArrayList<>(receiver.getFeedbacks());
        receiverFeedbacks.remove(feedback);
        receiver.setFeedbacks(receiverFeedbacks);
        float newRating = 0.0f;
        for (Feedback feedback1 : receiverFeedbacks) {
            newRating += feedback1.getRating();
        }
        if (receiverFeedbacks.size() > 0) {
            newRating /= receiverFeedbacks.size();
        }
        receiver.setRating(newRating);
        feedbackRepository.delete(feedback);
        userRepository.save(receiver);

        return feedback;
    }
}

