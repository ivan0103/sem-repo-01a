package nl.tudelft.sem.authentication.entities;

import java.util.List;
import java.util.Objects;


public class Student extends User {

    private String role;

    /**
     * Default constructor.
     */

    protected Student() {
        super();
    }

    /**
     * Initialises a student object.
     *
     * @param netID netId of the student - acts as primary key
     * @param name name of the student
     * @param rating rating of the student
     */

    protected Student(String netID, String name, Float rating) {
        super(netID, name, rating);
        this.role = "student";
    }

    /**
     * Initialises a student object.
     *
     * @param netID netId of the student - acts as primary key
     * @param name name of the student
     * @param rating rating of the student
     * @param feedbacks list of feedback received by the student from other users
     */

    protected Student(String netID, String name, Float rating, List<Feedback> feedbacks) {
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Student student = (Student) o;
        return Objects.equals(this.getNetID(), student.getNetID())
                && Objects.equals(this.getName(), student.getName())
                && Objects.equals(this.getRating(), student.getRating())
                && Objects.equals(this.getFeedbacks(), student.getFeedbacks())
                && Objects.equals(role, student.role);
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

