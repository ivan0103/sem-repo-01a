package nl.tudelft.sem.users;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import org.junit.jupiter.api.Test;


public class UserFactoryTest {

    private final transient String netID = "ad";
    private final transient String name = "ad";
    private final transient Float rating = 12.0f;
    private final transient List<Feedback> feedbacks = new ArrayList<>();
    private final transient String roleStudent = "student";
    private final transient String roleCompany = "company";

    @Test
    public void testCreateStudentNoFeedback() {
        User user = new UserFactory().createUser(netID, name, rating, roleStudent);
        assertNotNull(user);
        assertTrue(user instanceof Student);
    }

    @Test
    public void testCreateStudent() {
        User user = new UserFactory().createUser(netID, name, rating, feedbacks, roleStudent);
        assertNotNull(user);
        assertTrue(user instanceof Student);
    }

    @Test
    public void testCreateCompanyNoFeedback() {
        User user = new UserFactory().createUser(netID, name, rating, roleCompany);
        assertNotNull(user);
        assertTrue(user instanceof Company);
    }

    @Test
    public void testCreateCompany() {
        User user = new UserFactory().createUser(netID, name, rating, feedbacks, roleCompany);
        assertNotNull(user);
        assertTrue(user instanceof Company);
    }
}
