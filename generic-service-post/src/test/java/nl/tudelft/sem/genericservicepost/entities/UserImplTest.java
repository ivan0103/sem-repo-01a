package nl.tudelft.sem.genericservicepost.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserImplTest {

    private transient UserImpl user1;
    private transient UserImpl user2;
    private transient UserImpl user3;
    private transient UserImpl user4;
    private transient UserImpl user5;
    private transient UserImpl user6;


    @BeforeEach
    void setUp() {
        user1 = new UserImpl();
        String netid1 = "netid1";
        String name1 = "name1";
        user1.setNetID(netid1);
        user1.setRating(1.0f);
        user1.setName(name1);
        Feedback feedback1 = new Feedback();
        user1.setFeedbacks(List.of(feedback1));

        user2 = new UserImpl();
        user2.setNetID(netid1);
        user2.setRating(2.0f);
        user2.setName(name1);
        user2.setFeedbacks(List.of());

        user3 = new UserImpl();
        user3.setNetID(netid1);
        user3.setRating(1.0f);
        user3.setName(name1);
        user3.setFeedbacks(List.of());

        user4 = new UserImpl();
        user4.setNetID(netid1);
        user4.setRating(1.0f);
        user4.setName("name4");
        user4.setFeedbacks(List.of());

        user5 = new UserImpl();
        user5.setNetID(netid1);
        user5.setRating(1.0f);
        user5.setName(name1);
        user5.setFeedbacks(List.of(feedback1));

        user6 = new UserImpl();
        user6.setNetID("netid6");
        user6.setRating(1.0f);
        user6.setName("name6");
        user6.setFeedbacks(List.of());
    }

    @Test
    void testEqualsSame() {
        assertThat(user1).isEqualTo(user1);
    }

    @Test
    void testEqualsDifferentType() {
        assertThat(user1).isNotEqualTo("string");
    }

    @Test
    void testEqualsNull() {
        assertThat(user1).isNotEqualTo(null);
    }

    @Test
    void testEqualsDifferent1() {
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void testEqualsDifferent2() {
        assertThat(user1).isNotEqualTo(user3);
    }

    @Test
    void testEqualsDifferent3() {
        assertThat(user1).isNotEqualTo(user4);
    }

    @Test
    void testEqualsDifferent4() {
        assertThat(user1).isNotEqualTo(user6);
    }

    @Test
    void testEquals() {
        assertThat(user1).isEqualTo(user5);
    }

    @Test
    void testHashCode() {
        assertThat(user1.hashCode()).isEqualTo(user5.hashCode());
    }
}
