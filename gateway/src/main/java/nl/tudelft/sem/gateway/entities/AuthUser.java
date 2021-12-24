package nl.tudelft.sem.gateway.entities;

import java.util.Objects;

public class AuthUser {

    private String netID;
    private String password;
    private String role = null;

    public AuthUser() {

    }

    /**
     * AuthUser constructor.
     *
     * @param netID the net id
     * @param password the password
     * @param role the role of the authenticated user
     */

    public AuthUser(String netID, String password, String role) {
        this.netID = netID;
        this.password = password;
        if (role.equals("Student") || role.equals("Company")) {
            this.role = role;
        }
    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(netID, authUser.netID)
                && Objects.equals(password, authUser.password)
                && Objects.equals(role, authUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, password, role);
    }

    @Override
    public String toString() {
        return "AuthUser{"
                + "netID='" + netID + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + '}';
    }
}


