package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity(name = "student")
@Table
public class Student extends User {
    @Column(name = "role")
    private String role;

    /**
     * Default constructor.
     */

    public Student() {
        super();
    }

    /**
     * Initialises a student object.
     *
     * @param netID netId of the student - acts as primary key
     * @param name name of the student
     * @param rating rating of the student
     * @param feedbacks list of feedback received by the student from other users
     */

    public Student(String netID, String name, Float rating, List<Feedback> feedbacks) {
        super(netID, name, rating, feedbacks);
        this.role = "student";
    }

    /**
     * Getter for role.
     *
     * @return the role of the student
     */

    public String getRole() {
        return role;
    }

    /**
     * Setter for role.
     *
     * @param role the new role of the student
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Equals method - checks whether the students are the same or not.
     *
     * @param o the object used to compare the student
     * @return true - the students represent the same instance
     *         false - otherwise
     */

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && getClass() == o.getClass()
                && Objects.equals(role, ((Student) o).role);
    }

    /**
     * HashCode method - It generates a hashCode to student.
     *
     * @return a hashCode for each student
     */

    @Override
    public int hashCode() {
        return Objects.hash(this.getNetID(), this.getName(),
                this.getRating(), this.getFeedbacks(), role);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the student
     */

    @Override
    public String toString() {
        return "Student{"
                + ", netID='" + this.getNetID() + '\''
                + ", name='" + this.getName() + '\''
                + ", rating=" + this.getRating()
                + ", feedbacks=" + this.getFeedbacks()
                + '}';
    }
}
