package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserController<T> {

    String valueId = "netID";
    String valueName = "name";
    String receiverId = "toNetID";

    List<T> getUsers();

    T getOneUser(@PathVariable(value = valueId) String netID);

    T addUser(@PathVariable(value = valueId) String netID,
              @PathVariable(value = valueName) String name);

    T deleteUser(@PathVariable(value = valueId) String netID);

    T updateUser(@PathVariable(value = valueId) String netID,
                 @PathVariable(value = valueName) String name);

    Feedback addFeedback(@PathVariable(value = valueId) String netID,
                         @PathVariable(value = "text") String text,
                         @PathVariable(value = "rating") Integer rating,
                         @PathVariable(value = receiverId) String toNetID);

    Feedback editFeedback(@PathVariable(value = valueId) String netID,
                          @PathVariable(value = "text", required = false) String text,
                          @PathVariable(value = "rating", required = false) Integer rating,
                          @PathVariable(value = "feedbackId") Long feedbackId,
                          @PathVariable(value = receiverId) String toNetID);

    Feedback deleteFeedback(@PathVariable(value = valueId) String netID,
                            @PathVariable(value = "feedbackId") Long feedbackId,
                            @PathVariable(value = receiverId) String toNetID);
}
