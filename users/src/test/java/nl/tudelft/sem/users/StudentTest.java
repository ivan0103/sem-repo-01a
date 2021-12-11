package nl.tudelft.sem.users;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;


public class StudentTest extends UserTest {

    private final transient String role = "student";

    @Override
    protected User createUser(String netID, String name, Float rating) {
        return new UserFactory().createUser(netID, name, rating, role);
    }

    @Override
    protected User createUser(String netID, String name, Float rating, List<Feedback> feedbacks) {
        return new UserFactory().createUser(netID, name, rating, feedbacks, role);
    }
}
