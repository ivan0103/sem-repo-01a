package nl.tudelft.sem.gateway.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthUserTest {

    AuthUser user;

    @BeforeEach
    void setUp() {
        user = new AuthUser("Baboon", "Baboon2", "Student");
    }



    @Test
    void constructorNullTest() {
        assertThat(user).isNotNull();
    }

    @Test
    void getNetIDTest() {
        assertThat(user.getNetID()).isEqualTo("Baboon");
    }

    @Test
    void setNetIDTest() {
        user.setNetID("Human");
        assertThat(user.getNetID()).isEqualTo("Human");
        user.setNetID("Baboon");
    }

    @Test
    void getPasswordTest() {
        assertThat(user.getPassword()).isEqualTo("Baboon2");
    }

    @Test
    void setPasswordTest() {
        user.setPassword("Baboon3");
        assertThat(user.getPassword()).isEqualTo("Baboon3");
        user.setPassword("Baboon2");
    }

    @Test
    void getRoleTest() {
        assertThat(user.getRole()).isEqualTo("Student");
    }

    @Test
    void setRoleTest() {
        user.setRole("Company");
        assertThat(user.getRole()).isEqualTo("Company");
        user.setRole("Student");
    }

    @Test
    void equalsTestDifferentUsername() {
        AuthUser user2 = new AuthUser("Orangutan", "Baboon2", "Student");
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
        AuthUser user2 = new AuthUser("Baboon", "Baboon2", "Student");
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
                "AuthUser{netID='Baboon', password='Baboon2', role='Student'}");
    }
}