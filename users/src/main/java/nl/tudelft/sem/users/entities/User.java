package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name = "user")
@Table
public class User {
    @Id
    @Column(name = "netID", nullable = false)
    private String netID;

    @Column(name = "name")
    private String name;

    @Column(name = "rating")
    private Float rating;

    @OneToMany
    private List<Feedback> feedback;

    @Column(name = "role")
    private Role role;

    /**
     * Default constructor.
     */

    public User() {

    }

    /**
     * Initialises a user object.
     *
     * @param netID netId of the user - acts as primary key
     * @param name name of the user
     * @param rating rating of the user
     * @param feedback list of feedback received by the user from other users
     * @param role role of the user
     */

    public User(String netID, String name, Float rating, List<Feedback> feedback, Role role) {
        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedback = feedback;
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
     * Getter for the feedback provided by other users.
     *
     * @return a list of feedbacks
     */

    public List<Feedback> getFeedback() {
        return feedback;
    }

    /**
     * Setter for the feedback list.
     *
     * @param feedback a new list of feedbacks
     */

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    /**
     * Getter for role.
     *
     * @return role of the user
     */

    public Role getRole() {
        return role;
    }

    /**
     * Setter for role.
     *
     * @param role the new role
     */

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Equals method - checks whether the users are the same or not.
     *
     * @param o the object used to compare the user
     * @return true - the users represent the same instance
     *         false - otherwise
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
        return Objects.equals(name, user.name)
                && Objects.equals(rating, user.rating)
                && Objects.equals(feedback, user.feedback)
                && Objects.equals(role, user.role);
    }

    /**
     * HashCode method - It generates a hashCode to user.
     *
     * @return a hashCode for each user
     */

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedback, role);
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
                + ", feedback=" + feedback
                + ", role=" + role
                + '}';
    }
}
