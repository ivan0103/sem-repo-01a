package nl.tudelft.sem.users.controllers;

import nl.tudelft.sem.users.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class StudentController {

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

}
