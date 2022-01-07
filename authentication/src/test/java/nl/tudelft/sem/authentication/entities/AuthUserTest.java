package nl.tudelft.sem.authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Objects;
import org.junit.jupiter.api.Test;


public class AuthUserTest {

    private final transient String student = "student";
    private final transient String company = "company";
    private final transient String netID = "aa";
    private final transient String password = "b";
    private final transient AuthUser authUser = new AuthUser(netID, password, student);

    @Test
    public void testEmptyConstructor() {
        AuthUser authUser = new AuthUser();
        assertNotNull(authUser);
    }

    @Test
    public void testNonEmptyConstructor() {
        assertNotNull(authUser);
    }

    @Test
    public void testInvalidRoles() {
        AuthUser authUser = new AuthUser(netID, password, "blaaaah");
        assertNotNull(authUser);
        assertNull(authUser.getRole());
    }

    @Test
    public void testGetNetID() {
        assertEquals(netID, authUser.getNetID());
    }

    @Test
    public void testSetNetID() {
        String newNetID = "newNetID";
        authUser.setNetID(newNetID);
        assertEquals(newNetID, authUser.getNetID());
        authUser.setNetID(netID);
        assertEquals(netID, authUser.getNetID());
    }

    @Test
    public void testGetPassword() {
        assertEquals(password, authUser.getPassword());
    }

    @Test
    public void testSetPassword() {
        String newPassword = "newPassword";
        authUser.setPassword(newPassword);
        assertEquals(newPassword, authUser.getPassword());
        authUser.setPassword(password);
        assertEquals(password, authUser.getPassword());
    }

    @Test
    public void testGetRole() {
        assertEquals(student, authUser.getRole());
    }

    @Test
    public void testSetRole() {
        authUser.setRole(company);
        assertEquals(company, authUser.getRole());
        authUser.setRole(student);
        assertEquals(student, authUser.getRole());
    }

    @Test
    public void testEquals() {
        AuthUser authUser2 = new AuthUser(netID, password, student);
        assertEquals(authUser2, authUser);

        AuthUser authUser3 = new AuthUser();
        assertNotEquals(authUser3, authUser);

        AuthUser authUser4 = new AuthUser(netID, password, company);
        assertNotEquals(authUser4, authUser);

        String newNetID = "newNetID";
        AuthUser authUser5 = new AuthUser(newNetID, password, student);
        assertNotEquals(authUser5, authUser);

        String newPassword = "newPassword";
        AuthUser authUser6 = new AuthUser(netID, newPassword, student);
        assertNotEquals(authUser6, authUser);

        AuthUser authUser7 = new AuthUser(netID, newPassword, company);
        assertNotEquals(authUser7, authUser);

        AuthUser authUser8 = new AuthUser(newNetID, password, company);
        assertNotEquals(authUser8, authUser);

        AuthUser authUser9 = new AuthUser(newNetID, newPassword, company);
        assertNotEquals(authUser9, authUser);
    }

    @Test
    public void testHashCode() {
        int hashCode = Objects.hash(netID, password, student);
        assertEquals(hashCode, authUser.hashCode());

        int wrongHash = Objects.hash(netID, password, company);
        assertNotEquals(wrongHash, authUser.hashCode());
    }

    @Test
    public void testToString() {
        String toString = "AuthUser{"
                + "netID='" + netID + '\''
                + ", password='" + password + '\''
                + ", role='" + student + '\''
                + '}';
        assertEquals(toString, authUser.toString());
    }
}
