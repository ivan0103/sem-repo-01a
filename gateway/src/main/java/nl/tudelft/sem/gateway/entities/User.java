package nl.tudelft.sem.gateway.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class User {

    private String netID;
    private String name;
    private Float rating;
    private List<Feedback> feedbacks;
    private String role;

    /**
     * Default constructor.
     */

    protected User() {

    }

    /**
     * Initialises a user object.
     *
     * @param netID netId of the user - acts as primary key
     * @param name name of the user
     * @param rating rating of the user
     * @param role role of the user
     */

    protected User(String netID, String name, Float rating, String role) {
        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedbacks = new ArrayList<>();
        this.role = role;
    }

    /**
     * Initialises a user object.
     *
     * @param netID netId of the user - acts as primary key
     * @param name name of the user
     * @param rating rating of the user
     * @param feedbacks list of feedbacks received by the user from other users
     */

    public User(String netID, String name, Float rating,
                List<Feedback> feedbacks, String role) {

        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedbacks = feedbacks;
        this.role = role;
    }

    /**
     * Getter for the netID. netIDs are unique for each user.
     *
     * @return a unique nedID
     */

    public String getNetID() {
        return netID;
    }

    /**
     * Setter for the netID. This may fail if the provided netID already exists in the database.
     *
     * @param netID the new netID of the user
     */

    public void setNetID(String netID) {
        this.netID = netID;
    }

    /**
     * Getter for the name. It does not have to be unique.
     *
     * @return the name of the user
     */

    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     *
     * @param name the new name of the user
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the rating of the user.
     *
     * @return the rating of the user
     */

    public Float getRating() {
        return rating;
    }

    /**
     * Setter for the rating.
     *
     * @param rating the new rating
     */

    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * Getter for the feedbacks provided by other users.
     *
     * @return a list of feedbacks
     */

    public List<Feedback> getFeedbacks() {
        return this.feedbacks;
    }

    /**
     * Setter for the feedback list.
     *
     * @param feedbacks a new list of feedbacks
     */

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * Getter for role.
     *
     * @return the role of the user
     */

    public String getRole() {
        return role;
    }

    /**
     * Setter for role.
     *
     * @param role the new role of the user
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Equals method - checks whether the users are the same or not.
     *
     * @param o the object used to compare the user
     * @return true - the users represent the same instance
     *         false - otherwise
     *
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(netID, user.netID)
                && Objects.equals(name, user.name)
                && Objects.equals(rating, user.rating)
                && Objects.equals(feedbacks, user.feedbacks)
                && Objects.equals(role, user.role);
    }

    /**
     * HashCode method - It generates a hashCode to user.
     *
     * @return a hashCode for each user
     */

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedbacks, role);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the user
     */

    @Override
    public String toString() {
        return "User{"
                + ", netID='" + netID + '\''
                + ", name='" + name + '\''
                + ", rating=" + rating
                + ", feedbacks=" + feedbacks
                + ", role=" + role
                + '}';
    }
}
