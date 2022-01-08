package nl.tudelft.sem.gateway.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CommunicationEntityTest {

    private transient CommunicationEntity communicationEntity;
    private transient String netId;
    private transient String name;
    private transient String password;

    @BeforeEach
    void setUp() {
        netId = "Chimp";
        name = "Bill";
        password = "Chimp1";
        communicationEntity = new CommunicationEntity(
                netId,
                name,
                password,
                "company");

    }

    @Test
    void getNetID() {
        assertThat(communicationEntity.getNetID()).isEqualTo("Chimp");
    }

    @Test
    void getName() {
        assertThat(communicationEntity.getName()).isEqualTo("Bill");
    }

    @Test
    void getPassword() {
        assertThat(communicationEntity.getPassword()).isEqualTo("Chimp1");
    }

    @Test
    void getRole() {
        CommunicationEntity communicationEntity2 = new CommunicationEntity(
                netId,
                name,
                password,
                "STUDENT");
        assertThat(communicationEntity2.getRole()).isEqualTo("student");
    }

    @Test
    void testEqualsSameInstance() {
        assertThat(communicationEntity.equals(communicationEntity)).isTrue();
    }

    @Test
    void testEqualsWrongEntity() {
        assertThat(communicationEntity.equals(name)).isFalse();
    }

    @Test
    void testEqualsTrue() {
        CommunicationEntity other = new CommunicationEntity(
                netId,
                name,
                password,
                "company");
        assertThat(communicationEntity.equals(other)).isTrue();
    }

    @Test
    void testEqualsFalse() {
        CommunicationEntity other = new CommunicationEntity(
                netId,
                "Benjamin",
                password,
                "company");
        assertThat(communicationEntity.equals(other)).isFalse();
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(
                communicationEntity.getNetID(),
                communicationEntity.getName(),
                communicationEntity.getPassword(),
                communicationEntity.getRole()
        );

        assertThat(communicationEntity.hashCode()).isEqualTo(expected);
    }

    @Test
    void testToString() {
        String expected = "CommunicationEntity{"
                + "netID='" + communicationEntity.getNetID() + '\''
                + ", name='" + communicationEntity.getName() + '\''
                + ", password='" + communicationEntity.getPassword() + '\''
                + ", role='" + communicationEntity.getRole() + '\''
                + '}';

        assertThat(communicationEntity.toString()).isEqualTo(expected);
    }
}