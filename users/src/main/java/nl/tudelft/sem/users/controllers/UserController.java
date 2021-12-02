package nl.tudelft.sem.users.controllers;

import java.util.List;

import nl.tudelft.sem.users.entities.Feedback;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserController<T> {

    List<T> getUsers();

    T getOneUser(@PathVariable(value = "netID") String netID);

    T addUser(@PathVariable(value = "netID") String netID,
              @PathVariable(value = "name") String name);

    Feedback addFeedback(@PathVariable(value = "netID") String netID,
                         @PathVariable(value = "text") String text,
                         @PathVariable(value = "rating") Integer rating);

}
