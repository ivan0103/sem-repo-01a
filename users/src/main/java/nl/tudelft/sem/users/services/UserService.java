package nl.tudelft.sem.users.services;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserService<T> {

    List<T> getUsers();

    T getOneUser(String netID);

    T addUser(String netID, String name);

    T deleteUser(String netID);

    T updateUser(String netID, String name);

    Feedback addFeedback(String netID, String text, Integer rating, String toNetID);

}
