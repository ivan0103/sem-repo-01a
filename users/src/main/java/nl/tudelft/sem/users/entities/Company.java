package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "company")
@DiscriminatorValue("Company")
public class Company extends User {
    @Column(name = "role")
    private String role;

    /**
     * Default constructor.
     */

    protected Company() {
        super();
    }

    /**
     * Initialises a company object.
     *
     * @param name name of the company - acts as primary key
     * @param rating rating of the company
     */

    protected Company(String name, Float rating) {
        super(name, name, rating);
        this.role = "company";
    }

    /**
     * Initialises a company object.
     *
     * @param name name of the company - acts as primary key
     * @param rating rating of the company
     * @param feedbacks list of feedback received by the company from other users
     */

    protected Company(String name, Float rating, List<Feedback> feedbacks) {
        super(name, name, rating, feedbacks);
        this.role = "company";
    }

    /**
     * Getter for role.
     *
     * @return the role of the company
     */

    public String getRole() {
        return role;
    }

    /**
     * Setter for role.
     *
     * @param role the new role of the company
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Equals method - checks whether the companies are the same or not.
     *
     * @param o the object used to compare the company
     * @return true - the companies represent the same instance
     *         false - otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;
        return Objects.equals(this.getNetID(), company.getNetID())
                && Objects.equals(this.getName(), company.getName())
                && Objects.equals(this.getRating(), company.getRating())
                && Objects.equals(this.getFeedbacks(), company.getFeedbacks())
                && Objects.equals(role, company.role);
    }

    /**
     * HashCode method - It generates a hashCode to company.
     *
     * @return a hashCode for each company
     */

    @Override
    public int hashCode() {
        return Objects.hash(this.getNetID(), this.getName(),
                this.getRating(), this.getFeedbacks(), role);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the company
     */

    @Override
    public String toString() {
        return "Company{"
                + ", name='" + this.getName() + '\''
                + ", rating=" + this.getRating()
                + ", feedbacks=" + this.getFeedbacks()
                + '}';
    }
}
