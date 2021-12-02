package nl.tudelft.sem.users.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * PostMapping for one specific student.
     *
     * @param netID the id of the new student
     * @param name the name of the new student
     * @return a new student
     */

    @PostMapping(path = "{netID}/{name}")
    public Student addUser(@PathVariable(value = "netID") String netID,
                           @PathVariable(value = "name") String name) {

        return studentService.addUser(netID, name);
    }

    /**
     * Post mapping for adding feedback.
     *
     * @param netID the id of the student
     * @param text the text of the feedback
     * @param rating the rating of the feedback
     * @return a new feedback
     */

    @PostMapping(path = "{netID}/{text}/{rating}")
    public Feedback addFeedback(@PathVariable(value = "netID") String netID,
                                @PathVariable(value = "text") String text,
                                @PathVariable(value = "rating") Integer rating) {

        return studentService.addFeedback(netID, text, rating);
    }

}
