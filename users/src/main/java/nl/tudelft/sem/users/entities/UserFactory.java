package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;


public class UserFactory {

    /**
     * Instantiates an instance of the user.
     *
     * @param netID the net id of the user
     * @param name the name of the user
     * @param rating the rating of the user
     * @param feedbackList the list of feedbacks the user has
     * @param role the role of the user
     * @return a new user or null
     */

    public User createUser(String netID, String name, Float rating,
                           List<Feedback> feedbackList, String role) {

        if (Objects.equals(role, "student")) {
            return new Student(netID, name, rating, feedbackList);
        }

        if (Objects.equals(role, "company")) {
            return new Company(netID, rating, feedbackList);
        }

        return null;
    }

    /**
     * Instantiates an instance of the user.
     *
     * @param netID the net id of the user
     * @param name the name of the user
     * @param rating the rating of the user
     * @param role the role of the user
     * @return a new user or null
     */

    public User createUser(String netID, String name, Float rating, String role) {
        if (Objects.equals(role, "student")) {
            return new Student(netID, name, rating);
        }

        if (Objects.equals(role, "company")) {
            return new Company(netID, rating);
        }

        return null;
    }

}
