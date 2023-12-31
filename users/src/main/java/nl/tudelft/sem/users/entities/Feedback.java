package nl.tudelft.sem.users.entities;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "feedback")
@Table
public class Feedback {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private Integer rating;

    @OneToOne
    private User user;

    /**
     * Default constructor.
     */

    public Feedback() {

    }

    /**
     * Constructor for feedback.
     *
     * @param text the actual feedback
     * @param rating the rating of the feedback
     * @param user the owner of the feedback
     */

    public Feedback(String text, Integer rating, User user) {
        this.text = text;
        this.rating = rating;
        this.user = user;
    }

    /**
     * Constructor for feedback.
     *
     * @param id the id of the feedback
     * @param text the actual feedback
     * @param rating the rating of the feedback
     * @param user the owner of the feedback
     */

    public Feedback(Long id, String text, Integer rating, User user) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.user = user;
    }

    /**
     * Constructor for feedback.
     *
     * @param id the id of the feedback
     * @param text the actual feedback
     * @param rating the rating of the feedback
     */

    public Feedback(Long id, String text, Integer rating) {
        this.id = id;
        this.text = text;
        this.rating = rating;
    }

    /**
     * Constructor for feedback. The id is auto-generated by the database.
     *
     * @param text the actual feedback
     * @param rating the rating of the feedback
     */

    public Feedback(String text, Integer rating) {
        this.text = text;
        this.rating = rating;
    }

    /**
     * Getter for id.
     *
     * @return id of feedback
     */

    public Long getId() {
        return this.id;
    }

    /**
     * Setter for id.
     *
     * @param id the new id
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for text.
     *
     * @return the text of the feedback
     */

    public String getText() {
        return text;
    }

    /**
     * Getter for rating.
     *
     * @return the rating
     */

    public Integer getRating() {
        return rating;
    }

    /**
     * Setter for text.
     *
     * @param text the new text
     */

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Setter for rating.
     *
     * @param rating the new rating
     */

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Getter for user.
     *
     * @return the user the made the feedback
     */

    public User getUser() {
        return user;
    }

    /**
     * Setter for user.
     *
     * @param user the new owner of the feedback
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Equals method.
     *
     * @param o the object used to compare the feedback
     * @return true - the feedbacks represent the same instance
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
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id)
                && Objects.equals(text, feedback.text)
                && Objects.equals(rating, feedback.rating)
                && Objects.equals(user, feedback.user);
    }

    /**
     * HashCode method - It generates a hashCode to feedback.
     *
     * @return a hashCode for each feedback
     */

    @Override
    public int hashCode() {
        return Objects.hash(id, text, rating, user);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the user
     */

    @Override
    public String toString() {
        return "Feedback{"
                + "id=" + id
                + ", text='" + text + '\''
                + ", rating=" + rating
                + ", user=" + user
                + '}';
    }
}
