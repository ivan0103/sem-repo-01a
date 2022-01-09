package nl.tudelft.sem.genericservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeedbackTest {

    private transient Feedback feedback;
    private transient UserImpl user;
    private final transient String text = "text";

    @BeforeEach
    void setup() {
        user = new UserImpl();
        String netid = "netid";
        user.setNetID(netid);
        String name = "name";
        user.setName(name);
        user.setRating(1.0f);
        user.setFeedbacks(List.of());
        feedback = new Feedback(42L, text, 1, user);
    }

    @Test
    void constructors() {
        Feedback f1 = new Feedback();
        assertThat(f1).isNotNull();

        Feedback f2 = new Feedback("text tmp", 2, null);
        assertThat(f2).isNotNull();

        Feedback f3 = new Feedback(10L, "text tmp2", 3);
        assertThat(f3).isNotNull();

        Feedback f4 = new Feedback("text tmp3", 4);
        assertThat(f4).isNotNull();
    }

    @Test
    void getId() {
        assertThat(feedback.getId()).isEqualTo(42);
    }

    @Test
    void setId() {
        feedback.setId(69L);
        assertThat(feedback.getId()).isNotEqualTo(69);
    }

    @Test
    void getText() {
        assertThat(feedback.getText()).isEqualTo(new String(text));
        // new string to check actual equality
    }

    @Test
    void getRating() {
        assertThat(feedback.getRating()).isEqualTo(1);
    }

    @Test
    void setText() {
        feedback.setText("new text");
        assertThat(feedback.getText()).isEqualTo("new text");
    }

    @Test
    void setRating() {
        feedback.setRating(2);
        assertThat(feedback.getRating()).isEqualTo(2);
    }

    @Test
    void getUser() {
        assertThat(feedback.getUser()).isEqualTo(user);
    }

    @Test
    void setUser() {
        UserImpl newUser = new UserImpl();
        newUser.setNetID("lalala");
        feedback.setUser(newUser);
        assertThat(feedback.getUser()).isEqualTo(newUser);
    }

    @Test
    void testEqualsSame() {
        assertThat(feedback).isEqualTo(feedback);
    }

    @Test
    void testEqualsNull() {
        assertThat(feedback).isNotEqualTo(null);
    }

    @Test
    void testEqualsDifferentType() {
        assertThat(feedback).isNotEqualTo(new GenericPost());
    }

    @Test
    void testEquals1() {
        Feedback tmp = new Feedback();
        assertThat(feedback).isNotEqualTo(tmp);
    }

    @Test
    void testEquals2() {
        Feedback tmp = new Feedback(42L, "not the same", 2);
        tmp.setId(42L);
        assertThat(feedback).isNotEqualTo(tmp);
    }

    @Test
    void testEquals3() {
        Feedback tmp = new Feedback(42L, text, 4);
        assertThat(feedback).isNotEqualTo(tmp);
    }

    @Test
    void testEquals4() {
        Feedback tmp = new Feedback(42L, text, 1);
        assertThat(feedback).isNotEqualTo(tmp);
    }

    @Test
    void testEquals() {
        Feedback tmp = new Feedback(42L, text, 1, user);
        assertThat(feedback).isEqualTo(tmp);
    }

    @Test
    void testHashCode() {
        assertThat(feedback.hashCode()).isEqualTo(Objects.hash(42L, text, 1, user));
    }

    @Test
    void testToString() {
        String expected = "Feedback{id=42, text='text', rating=1, user="
                + user.toString()
                + "}";
        assertThat(feedback.toString()).isEqualTo(expected);
    }
}