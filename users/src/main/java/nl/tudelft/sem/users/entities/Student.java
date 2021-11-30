package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name = "student")
@Table
public class Student extends User {
    @Id
    @Column(name = "netID", nullable = false)
    private String netID;

    @Column(name = "name")
    private String name;

    @Column(name = "rating")
    private Float rating;

    @OneToMany
    private List<Feedback> feedback;

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
     * @param feedback list of feedback received by the student from other users
     */

    public Student(String netID, String name, Float rating, List<Feedback> feedback) {
        super(netID, name, rating, feedback);
        this.netID = netID;
        this.name = name;
        this.rating = rating;
        this.feedback = feedback;
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
        return super.equals(o) && getClass() == o.getClass();
    }

    /**
     * HashCode method - It generates a hashCode to student.
     *
     * @return a hashCode for each student
     */

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedback);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the student
     */

    @Override
    public String toString() {
        return "Student{"
                + ", netID='" + netID + '\''
                + ", name='" + name + '\''
                + ", rating=" + rating
                + ", feedback=" + feedback
                + '}';
    }
}
