package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.List;
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
}
