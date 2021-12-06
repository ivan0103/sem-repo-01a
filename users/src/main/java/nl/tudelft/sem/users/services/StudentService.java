package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService implements UserService<Student> {

    private final transient UserRepository studentRepository;
    private final transient FeedbackRepository feedbackRepository;

    /**
     * Constructor of UserService - It instantiates a new StudentService object.
     *
     * @param studentRepository repository injected with data from the database
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public StudentService(UserRepository studentRepository, FeedbackRepository feedbackRepository) {

        this.studentRepository = studentRepository;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Getter for all the students in the database.
     *
     * @return a list of students contained in the database
     */

    @Override
    public List<Student> getUsers() {
        List<Student> students = new ArrayList<>();

        for (User user : studentRepository.findAll()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }

        return students;
    }

    /**
     * Retrieves only one student based on the id.
     *
     * @param netID - the id of the student to be retrieved
     * @return the student
     */

    @Override
    public Student getOneUser(String netID) {
        if (studentRepository.findById(netID).isEmpty()) {
            //throw new IllegalStateException("Student nonexistent :/");
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        return (Student) studentRepository.findById(netID).get();
    }

    /**
     * Creates a new user.
     *
     * @param netID the netID of the new user
     * @param name the name of the new user
     * @return a new user
     */

    @Override
    public Student addUser(String netID, String name) {
        if (studentRepository.findById(netID).isPresent()
            && (studentRepository.findById(netID).get() instanceof Student)) {

            return (Student) studentRepository.findById(netID).get();
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        Student student = (Student) new UserFactory().createUser(netID, name, 0.0f, "student");
        studentRepository.save(student);
        return student;
    }

    /**
     * Creates a new feedback.
     *
     * @param netID id of the student
     * @param text text of the feedback
     * @param rating rating of the feedback
     * @param toNetID the netID of the user that receives the feedback
     * @return a new feedback
     */

    @Override
    public Feedback addFeedback(String netID, String text, Integer rating, String toNetID) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        if (studentRepository.findById(toNetID).isEmpty()) {
            return null;
        }

        Student student = (Student) studentRepository.findById(netID).get();
        Feedback feedback = new Feedback(text, rating, student);
        User toUser = studentRepository.findById(toNetID).get();
        toUser.addFeedback(feedback);
        toUser.setRating((toUser.getRating() * (toUser.getFeedbacks().size() - 1)
                + feedback.getRating()) / (toUser.getFeedbacks().size()));
        studentRepository.save(toUser);

        return feedbackRepository.findTopByOrderByIdDesc();
    }

    /**
     * Deletes the student with the corresponding netID.
     *
     * @param netID the id of the student
     * @return the student that was deleted
     */

    @Override
    public Student deleteUser(String netID) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        Student student = (Student) studentRepository.findById(netID).get();
        List<User> users = studentRepository.findAll();
        List<Feedback> removals = new ArrayList<>();

        for (User user : users) {
            List<Feedback> keeps = new ArrayList<>();

            for (Feedback feedback : user.getFeedbacks()) {
                if (feedback.getUser().equals(student)) {
                    removals.add(feedback);
                } else {
                    keeps.add(feedback);
                }
            }

            user.setFeedbacks(keeps);
            studentRepository.save(user);
        }

        feedbackRepository.deleteAll(removals);
        studentRepository.delete(student);
        return student;
    }

    /**
     * Updates information of student.
     *
     * @param netID the id of the student
     * @param name the new name of student
     * @return an updated student
     */

    @Override
    public Student updateUser(String netID, String name) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        Student student = (Student) studentRepository.findById(netID).get();
        student.setName(name);
        studentRepository.save(student);
        return student;
    }

    /**
     * Allows students to edit their feedback.
     *
     * @param netID the id of the student
     * @param text the new text of the feedback
     * @param rating the new rating of the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the student that received the feedback
     * @return an edited feedback
     */

    @Override
    public Feedback editFeedback(String netID, String text,
                                 Integer rating, Long feedbackId, String toNetID) {

        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        if (studentRepository.findById(toNetID).isEmpty()) {
            return null;
        }

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            return null;
        }

        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        Student student = (Student) studentRepository.findById(netID).get();
        User receiver = studentRepository.findById(toNetID).get();

        if (!feedback.getUser().equals(student) || !receiver.getFeedbacks().contains(feedback)) {
            return null;
        }

        receiver.removeFeedback(feedback);

        if (text != null) {
            feedback.setText(text);
        }

        if (rating != null) {
            feedback.setRating(rating);
        }

        feedbackRepository.save(feedback);
        receiver.addFeedback(feedback);
        receiver.setRating((receiver.getRating() * (receiver.getFeedbacks().size() - 1)
                + feedback.getRating()) / (receiver.getFeedbacks().size()));
        studentRepository.save(receiver);

        return feedback;
    }

    /**
     * Deletes a feedback.
     *
     * @param netID the id of the student that created the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return the deleted feedback
     */

    @Override
    public Feedback deleteFeedback(String netID, Long feedbackId, String toNetID) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(toNetID).isEmpty()) {
            return null;
        }

        if (studentRepository.findById(netID).isPresent()
            && !(studentRepository.findById(netID).get() instanceof Student)) {

            return null;
        }

        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            return null;
        }

        Student student = (Student) studentRepository.findById(netID).get();
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        User receiver = studentRepository.findById(toNetID).get();

        if (!feedback.getUser().equals(student) || !receiver.getFeedbacks().contains(feedback)) {
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
        studentRepository.save(receiver);

        return feedback;
    }
}
