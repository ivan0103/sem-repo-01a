package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "students")
public class StudentController implements UserController<Student> {

    private final transient StudentService studentService;

    /**
     * This method sets up all the necessary services and is called when the server is started.
     *
     * @param studentService the student service
     */

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GetMapping for students.
     *
     * @return a list of students
     */

    @GetMapping
    public List<Student> getUsers() {
        return studentService.getUsers();
    }

    /**
     * GetMapping for one specific student.
     *
     * @param netID the id of the student we want to find
     * @return the student we look for or null
     */

    @GetMapping(path = "{netID}")
    public Student getOneUser(@PathVariable(value = "netID") String netID) {
        return studentService.getOneUser(netID);
    }

}
