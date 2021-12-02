package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserController<T> {

    String valueId = "netID";
    String valueName = "name";

    List<T> getUsers();

    T getOneUser(@PathVariable(value = valueId) String netID);

    T addUser(@PathVariable(value = valueId) String netID,
              @PathVariable(value = valueName) String name);

    T deleteUser(@PathVariable(value = valueId) String netID);

    T updateUser(@PathVariable(value = valueId) String netID,
                 @PathVariable(value = valueName) String name,
                 @PathVariable(value = "newNetID", required = false) String newNetID);

    Feedback addFeedback(@PathVariable(value = valueId) String netID,
                         @PathVariable(value = "text") String text,
                         @PathVariable(value = "rating") Integer rating);

}
