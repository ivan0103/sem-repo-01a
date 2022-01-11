package nl.tudelft.sem.users.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;


abstract class UserTest {

    private final transient String netID = "An";
    private final transient String name = "An";
    private final transient Float rating = 0.2f;
    private final transient List<Feedback> feedbacks = List.of(new Feedback(1L, "Aha", 2, null));
    private final transient User user = createUser(netID, name, rating, feedbacks);

    protected abstract User createUser(String netID, String name, Float rating);

    protected abstract User createUser(String netID, String name, Float rating,
                                       List<Feedback> feedbacks);

    @Test
    public void testConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testFirstConstructor() {
        User user2 = createUser(netID, name, rating);
        assertNotNull(user2);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(netID, user.getNetID());
        assertEquals(name, user.getName());
        assertEquals(rating, user.getRating());
        assertEquals(feedbacks, user.getFeedbacks());

        final String newNetID = "rea";
        final String newName = "nam";
        final Float newRating = 1.2f;
        final List<Feedback> newFeedbacks = new ArrayList<>();

        user.setNetID(newNetID);
        user.setName(newName);
        user.setRating(newRating);
        user.setFeedbacks(newFeedbacks);

        assertEquals(newNetID, user.getNetID());
        assertEquals(newName, user.getName());
        assertEquals(newRating, user.getRating());
        assertEquals(newFeedbacks, user.getFeedbacks());
    }

    @Test
    public void testEquals() {
        User user2 = createUser(netID, name, rating, feedbacks);
        assertEquals(user2, user);

        assertNotEquals(null, user);

        user2.setNetID("vvvvv");
        assertNotEquals(user2, user);
        user2.setNetID(netID);

        user2.setName("aaa");
        assertNotEquals(user2, user);
        user2.setName(name);

        user2.setRating(20f);
        assertNotEquals(user2, user);
        user.setRating(rating);

        user2.setFeedbacks(new ArrayList<>());
        assertNotEquals(user2, user);
        user.setFeedbacks(feedbacks);
    }

    @Test
    public void testHash() {
        if (user instanceof Student) {
            int hash = Objects.hash(netID, name, rating, feedbacks, "student");
            assertEquals(hash, user.hashCode());
        }

        if (user instanceof Company) {
            int hash = Objects.hash(netID, name, rating, feedbacks, "company");
            assertEquals(hash, user.hashCode());
        }
    }

    @Test
    public void testToString() {
        if (user instanceof Student) {
            String toString = "Student{"
                    + ", netID='" + user.getNetID() + '\''
                    + ", name='" + user.getName() + '\''
                    + ", rating=" + user.getRating()
                    + ", feedbacks=" + user.getFeedbacks()
                    + '}';

            assertEquals(toString, user.toString());
        }

        if (user instanceof Company) {
            String toString = "Company{"
                    + ", name='" + user.getName() + '\''
                    + ", rating=" + user.getRating()
                    + ", feedbacks=" + user.getFeedbacks()
                    + '}';

            assertEquals(toString, user.toString());
        }
    }

    @Test
    public void testGetAndSetRole() {
        if (user instanceof Student) {
            assertEquals("student", ((Student) user).getRole());

            ((Student) user).setRole("notStud");
            assertEquals("notStud", ((Student) user).getRole());
            ((Student) user).setRole("student");
        }

        if (user instanceof Company) {
            assertEquals("company", ((Company) user).getRole());

            ((Company) user).setRole("notComp");
            assertEquals("notComp", ((Company) user).getRole());
            ((Company) user).setRole("company");
        }
    }
}
