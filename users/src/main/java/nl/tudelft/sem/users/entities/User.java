package nl.tudelft.sem.users.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name = "user")
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
        name = "User")
public abstract class User {
    @Id
    @Column(name = "netID", nullable = false)
    private String netID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating")
    private Float rating;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

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
     */

    protected User(String netID, String name, Float rating) {
        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedbacks = new ArrayList<>();
    }

    /**
     * Initialises a user object.
     *
     * @param netID netId of the user - acts as primary key
     * @param name name of the user
     * @param rating rating of the user
     * @param feedbacks list of feedbacks received by the user from other users
     */

    protected User(String netID, String name, Float rating, List<Feedback> feedbacks) {
        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedbacks = feedbacks;
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
     * Equals method - checks whether the users are the same or not.
     * NOTE! Since this is an abstract class an equal method does not make much sense
     * to have it, but pmd complained about it, unfortunately.
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
                && Objects.equals(feedbacks, user.feedbacks);
    }

    /**
     * HashCode method - It generates a hashCode to user.
     *
     * @return a hashCode for each user
     */

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedbacks);
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
                + '}';
    }
}
