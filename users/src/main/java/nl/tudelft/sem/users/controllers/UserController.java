package nl.tudelft.sem.users.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserController<T> {

    List<T> getUsers();

    T getOneUser(@PathVariable(value = "netID") String netID);

}
