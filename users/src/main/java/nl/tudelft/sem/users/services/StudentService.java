package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService implements UserService<Student> {

    private final transient StudentRepository studentRepository;
    private final transient FeedbackRepository feedbackRepository;

    /**
     * Constructor of UserService - It instantiates a new StudentService object.
     *
     * @param studentRepository repository injected with data from the database
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          FeedbackRepository feedbackRepository) {

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
        return studentRepository.findAll();
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

        return studentRepository.findById(netID).get();
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
        if (studentRepository.findById(netID).isPresent()) {
            return studentRepository.findById(netID).get();
        }

        Student student = new Student(netID, name, 0.0f, new ArrayList<>());
        studentRepository.save(student);
        return student;
    }

    /**
     * Creates a new feedback.
     *
     * @param netID id of the student
     * @param text text of the feedback
     * @param rating rating of the feedback
     * @return a new feedback
     */

    @Override
    public Feedback addFeedback(String netID, String text, Integer rating) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        Feedback feedback = new Feedback(text, rating);
        feedbackRepository.save(feedback);
        Student student = studentRepository.findById(netID).get();
        student.addFeedback(feedback);
        student.setRating((student.getRating() * (student.getFeedbacks().size() - 1)
                + ((float) feedback.getRating())) / ((float) student.getFeedbacks().size()));
        studentRepository.save(student);

        return feedback;
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

        Student student = studentRepository.findById(netID).get();
        List<Feedback> feedbacks = new ArrayList<>(student.getFeedbacks());
        studentRepository.delete(student);
        feedbackRepository.deleteAll(feedbacks);

        return student;
    }

    /**
     * Updates information of student.
     *
     * @param netID the id of the student
     * @param name the new name of student
     * @param newNetID the new id of student (optional)
     * @return an updated student
     */

    @Override
    public Student updateUser(String netID, String name, String newNetID) {
        if (studentRepository.findById(netID).isEmpty()) {
            return null;
        }

        Student student = studentRepository.findById(netID).get();

        if (Objects.equals(newNetID, "null") || newNetID == null
            || studentRepository.findById(newNetID).isPresent()) {

            student.setName(name);
            studentRepository.save(student);
            return student;
        }

        studentRepository.delete(student);
        student.setName(name);
        student.setNetID(newNetID);
        studentRepository.save(student);
        return student;
    }
}
