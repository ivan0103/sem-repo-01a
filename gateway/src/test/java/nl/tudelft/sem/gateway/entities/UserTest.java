package nl.tudelft.sem.gateway.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class UserTest {

    private transient User user;
    private transient ArrayList<Feedback> feedbacks;
    private transient Feedback feedback;
    private transient String netId;
    private transient String name;
    private transient String role;

    @BeforeEach
    void setUp() {
        feedback = new Feedback(1L, "Inappropriate", 2);
        feedbacks = new ArrayList<>();
        netId = "Bonobo";
        name = "Ben";
        role = "student";
        user = new User(netId, name, 2f, feedbacks, role);
    }

    @Test
    void constructorEmpty() {
        User user2 = new User();
        assertThat(user2).isNotNull();
    }

    @Test
    void constructorNoFeedbacks() {
        User user2 = new User(netId, name, 2f, role);
        assertThat(user2).isNotNull();
    }

    @Test
    void getNetID() {
        assertThat(user.getNetID()).isEqualTo(netId);
    }

    @Test
    void setNetID() {
        user.setNetID("Bonobo2");

        assertThat(user.getNetID()).isEqualTo("Bonobo2");

        user.setNetID(netId);
    }

    @Test
    void getName() {
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void setName() {
        user.setName("Brocollus");

        assertThat(user.getName()).isEqualTo("Brocollus");

        user.setName(name);
    }

    @Test
    void getRating() {
        assertThat(user.getRating()).isEqualTo(2f);
    }

    @Test
    void setRating() {
        user.setRating(3f);

        assertThat(user.getRating()).isEqualTo(3f);

        user.setRating(2f);
    }

    @Test
    void getFeedbacks() {
        assertThat(user.getFeedbacks()).isEqualTo(feedbacks);
    }

    @Test
    void setFeedbacks() {
        ArrayList<Feedback> feedbacks2 = new ArrayList<>();
        user.setFeedbacks(feedbacks2);

        assertThat(user.getFeedbacks()).isEqualTo(feedbacks2);

        user.setFeedbacks(feedbacks);

    }

    @Test
    void getRole() {
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    void setRole() {
        user.setRole("company");
        assertThat(user.getRole()).isEqualTo("company");
        user.setRole(role);
    }

    @Test
    void testEqualsSameInstance() {
        assertThat(user.equals(user)).isTrue();
    }

    @Test
    void testEqualsWrongObject() {
        assertThat(user.equals(2)).isFalse();
    }

    @Test
    void testEqualsTrue() {
        User user2 = new User(netId, name, 2f, feedbacks, role);
        assertThat(user.equals(user2)).isTrue();
    }

    @Test
    void testEqualsFalse() {
        User user2 = new User(netId, name, 2f, feedbacks, "company");
        assertThat(user.equals(user2)).isFalse();
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(
                user.getNetID(),
                user.getName(),
                user.getRating(),
                user.getFeedbacks(),
                user.getRole()
        );

        assertThat(user.hashCode()).isEqualTo(expected);
    }

    @Test
    void testToString() {
        String expected = "User{"
                + ", netID='" + user.getNetID() + '\''
                + ", name='" + user.getName() + '\''
                + ", rating=" + user.getRating()
                + ", feedbacks=" + user.getFeedbacks()
                + ", role=" + user.getRole()
                + '}';

        assertThat(user.toString()).isEqualTo(expected);
    }
}