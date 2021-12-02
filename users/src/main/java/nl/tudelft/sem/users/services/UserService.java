package nl.tudelft.sem.users.services;

import java.util.List;


public interface UserService<T> {

    List<T> getUsers();

    T getOneUser(String netID);

    T addUser(String netID, String name);

}
