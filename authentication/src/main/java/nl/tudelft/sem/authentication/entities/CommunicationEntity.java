package nl.tudelft.sem.authentication.entities;

import java.util.Locale;
import java.util.Objects;

public class CommunicationEntity {

    private final transient String netID;
    private final transient String name;
    private final transient String password;
    private transient String role = null;

    /**
     * Constructor for communication entity.
     *
     * @param netID the netID of the entity
     * @param name the name of the entity
     * @param password the password of the entity
     * @param role the role of the entity
     */

    public CommunicationEntity(String netID, String name, String password, String role) {
        this.netID = netID;
        this.name = name;
        this.password = password;
        if (role.equalsIgnoreCase("student")) {
            this.role = role.toLowerCase(Locale.ENGLISH);
        } else if (role.equalsIgnoreCase("company")) {
            this.role = role.toLowerCase(Locale.ENGLISH);
        }
    }

    public String getNetID() {
        return netID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommunicationEntity that = (CommunicationEntity) o;
        return Objects.equals(netID, that.netID)
                && Objects.equals(name, that.name)
                && Objects.equals(password, that.password)
                && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, password, role);
    }

    @Override
    public String toString() {
        return "CommunicationEntity{"
                + "netID='" + netID + '\''
                + ", name='" + name + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + '}';
    }
}
