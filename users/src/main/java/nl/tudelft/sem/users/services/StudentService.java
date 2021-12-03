package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.entities.User;
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

        Student student = new Student(netID, name, 0.0f);
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
                + ((float) feedback.getRating())) / ((float) toUser.getFeedbacks().size()));
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
}
