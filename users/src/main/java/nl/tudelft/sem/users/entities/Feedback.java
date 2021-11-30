package nl.tudelft.sem.users.entities;

import java.util.Objects;
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

    @OneToOne
    private User user;

    @Column(name = "rating")
    private Integer rating;

    public Feedback() {

    }

    /**
     * Constructor for feedback.
     *
     * @param id the id of the feedback
     * @param text the actual feedback
     * @param user the feedback giver
     * @param rating the rating of the feedback
     */

    public Feedback(Long id, String text, User user, Integer rating) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.rating = rating;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        //Doesn't do anything. Used only to suppress warnings
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        //Doesn't do anything. Used only to suppress warnings
    }

    public Integer getRating() {
        return rating;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

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
                && Objects.equals(user, feedback.user)
                && Objects.equals(rating, feedback.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, user, rating);
    }

    @Override
    public String toString() {
        return "Feedback{"
                + "id=" + id
                + ", text='" + text + '\''
                + ", user=" + user
                + ", rating=" + rating
                + '}';
    }
}
