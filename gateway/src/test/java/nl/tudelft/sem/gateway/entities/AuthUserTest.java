package nl.tudelft.sem.gateway.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AuthUserTest {

    private transient AuthUser user;
    private final transient String baboon = "Baboon";
    private final transient String baboon2 = "Baboon2";
    private final transient String student = "student";

    @BeforeEach
    void setUp() {
        user = new AuthUser(baboon, baboon2, student);
    }

    @Test
    void constructorNullTest() {
        assertThat(user).isNotNull();
    }

    @Test
    void getNetIDTest() {
        assertThat(user.getNetID()).isEqualTo(baboon);
    }

    @Test
    void setNetIDTest() {
        user.setNetID("Human");
        assertThat(user.getNetID()).isEqualTo("Human");
        user.setNetID(baboon);
    }

    @Test
    void getPasswordTest() {
        assertThat(user.getPassword()).isEqualTo(baboon2);
    }

    @Test
    void setPasswordTest() {
        user.setPassword("Baboon3");
        assertThat(user.getPassword()).isEqualTo("Baboon3");
        user.setPassword(baboon2);
    }

    @Test
    void getRoleTest() {
        assertThat(user.getRole()).isEqualTo(student);
    }

    @Test
    void setRoleTest() {
        user.setRole("Company");
        assertThat(user.getRole()).isEqualTo("Company");
        user.setRole(student);
    }

    @Test
    void equalsTestDifferentUsername() {
        AuthUser user2 = new AuthUser("Orangutan", baboon2, student);
        assertThat(user.equals(user2)).isFalse();
    }

    @Test
    void equalsTestWrongObject() {
        String user2 = "some";
        assertThat(user.equals(user2)).isFalse();
    }

    @Test
    void equalsTestSameInstance() {
        assertThat(user.equals(user)).isTrue();
    }

    @Test
    void equalsTestTrue() {
        AuthUser user2 = new AuthUser(baboon, baboon2, student);
        assertThat(user.equals(user2)).isTrue();
    }

    @Test
    void hashCodeTest() {
        assertThat(user.hashCode()).isEqualTo(
                Objects.hash(
                        user.getNetID(),
                        user.getPassword(),
                        user.getRole()));
    }

    @Test
    void testToStringTest() {
        assertThat(user.toString()).isEqualTo(
                "AuthUser{netID='Baboon', password='Baboon2', role='student'}");
    }
}