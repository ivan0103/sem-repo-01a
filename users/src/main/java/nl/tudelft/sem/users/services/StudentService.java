package nl.tudelft.sem.users.services;

import nl.tudelft.sem.users.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private final transient StudentRepository studentRepository;

    /**
     * Constructor of UserService - It instantiates a new StudentService object.
     *
     * @param studentRepository repository injected with data from the database
     */

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
