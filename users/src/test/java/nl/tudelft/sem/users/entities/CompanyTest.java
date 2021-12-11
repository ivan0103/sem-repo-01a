package nl.tudelft.sem.users.entities;

import java.util.List;


public class CompanyTest extends UserTest {

    private final transient String role = "company";

    @Override
    protected User createUser(String netID, String name, Float rating) {
        return new UserFactory().createUser(netID, name, rating, role);
    }

    @Override
    protected User createUser(String netID, String name, Float rating, List<Feedback> feedbacks) {
        return new UserFactory().createUser(netID, name, rating, feedbacks, role);
    }

}
