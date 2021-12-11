package nl.tudelft.sem.users.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.Test;


public class FeedbackTest {

    private final transient Long id = 1L;
    private final transient String text = "Depends";
    private final transient Integer rating = 10;
    private final transient User user =
            new UserFactory().createUser("a", "a", 20f, new ArrayList<>(), "student");
    private final transient Feedback feedback = new Feedback(id, text, rating, user);

    @Test
    public void testConstructor() {
        assertNotNull(feedback);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(id, feedback.getId());
        assertEquals(text, feedback.getText());
        assertEquals(rating, feedback.getRating());
        assertEquals(user, feedback.getUser());

        feedback.setText("Aaaa");
        assertEquals("Aaaa", feedback.getText());

        feedback.setRating(2);
        assertEquals(2, feedback.getRating());

        feedback.setUser(null);
        assertNull(feedback.getUser());
    }

    @Test
    public void testEquals() {
        Feedback sameFeedback = new Feedback(id, text, rating, user);
        assertEquals(sameFeedback, feedback);

        sameFeedback.setText("ww");
        assertNotEquals(sameFeedback, feedback);
        sameFeedback.setText(text);

        sameFeedback.setRating(2000);
        assertNotEquals(sameFeedback, feedback);
        sameFeedback.setRating(rating);

        sameFeedback.setUser(null);
        assertNotEquals(sameFeedback, feedback);
        sameFeedback.setUser(user);
    }

    @Test
    public void testHash() {
        int hash = Objects.hash(id, text, rating, user);
        assertEquals(hash, feedback.hashCode());
    }

    @Test
    public void testToString() {
        String toString = "Feedback{"
                + "id=" + id
                + ", text='" + text + '\''
                + ", rating=" + rating
                + ", user=" + user
                + '}';

        assertEquals(toString, feedback.toString());
    }
}

