package nl.tudelft.sem.users.controllers;

import nl.tudelft.sem.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final transient UserService userService;

    /**
     * This method sets up all the necessary services and is called when the server is started.
     * @param userService the user service
     */

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

}
