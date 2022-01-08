package nl.tudelft.sem.gateway.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class FeedbackTest {

    private transient Feedback feedback;
    private transient User user;
    private transient String text;

    @BeforeEach
    void setUp() {
        user = new User("Bonobo", "Ben", 2f, new ArrayList<>(), "student");
        text = "Inappropriate";
        feedback = new Feedback(1L, text, 2, user);
    }

    @Test
    void constructorEmpty() {
        Feedback feedback2 = new Feedback();
        assertThat(feedback2).isNotNull();
    }

    @Test
    void constructorNoId() {
        Feedback feedback2 = new Feedback(text, 2, user);
        assertThat(feedback2).isNotNull();
    }

    @Test
    void constructorNoUser() {
        Feedback feedback2 = new Feedback(1L, text, 2);
        assertThat(feedback2).isNotNull();
    }

    @Test
    void constructorNoIdOrUser() {
        Feedback feedback2 = new Feedback(text, 2);
        assertThat(feedback2).isNotNull();
    }

    @Test
    void getId() {
        assertThat(feedback.getId()).isEqualTo(1L);
    }

    @Test
    void setId() {
        feedback.setId(2L);
        assertThat(feedback.getId()).isEqualTo(1L);
    }

    @Test
    void getText() {
        assertThat(feedback.getText()).isEqualTo(text);
    }

    @Test
    void getRating() {
        assertThat(feedback.getRating()).isEqualTo(2);
    }

    @Test
    void setText() {
        feedback.setText("Cute");
        assertThat(feedback.getText()).isEqualTo("Cute");
        feedback.setText(text);
    }

    @Test
    void setRating() {
        feedback.setRating(3);
        assertThat(feedback.getRating()).isEqualTo(3);
        feedback.setRating(2);
    }

    @Test
    void getUser() {
        assertThat(feedback.getUser()).isEqualTo(user);
    }

    @Test
    void setUser() {
        User user2 = new User("Bonobo", "Bobathy", 2f, new ArrayList<>(), "student");
        feedback.setUser(user2);
        assertThat(feedback.getUser()).isEqualTo(user2);
        feedback.setUser(user);
    }

    @Test
    void testEqualsSameInstance() {
        assertThat(feedback.equals(feedback)).isTrue();
    }

    @Test
    void testEqualsWrongObject() {
        assertThat(feedback.equals("Bibby")).isFalse();
    }

    @Test
    void testEqualsFalse() {
        Feedback feedback2 = new Feedback(1L, "Cute", 2, user);
        assertThat(feedback.equals(feedback2)).isFalse();
    }

    @Test
    void testEqualsTrue() {
        Feedback feedback2 = new Feedback(1L, text, 2, user);
        assertThat(feedback.equals(feedback2)).isTrue();
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(
                feedback.getId(),
                feedback.getText(),
                feedback.getRating(),
                feedback.getUser());

        assertThat(feedback.hashCode()).isEqualTo(expected);
    }

    @Test
    void testToString() {
        String expected = "Feedback{"
                + "id=" + feedback.getId()
                + ", text='" + feedback.getText() + '\''
                + ", rating=" + feedback.getRating()
                + ", user=" + feedback.getUser()
                + '}';

        assertThat(feedback.toString()).isEqualTo(expected);
    }
}