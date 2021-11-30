package nl.tudelft.sem.users.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "company")
@Table
@Getter
@Setter
public class Company extends User {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating")
    private Float rating;

    @OneToMany
    private List<Feedback> feedback;

    private transient String netID;

    /**
     * Default constructor.
     */

    public Company() {
        super();
    }

    /**
     * Initialises a company object.
     *
     * @param name name of the company - acts as primary key
     * @param rating rating of the company
     * @param feedback list of feedback received by the company from other users
     */

    public Company(String name, Float rating, List<Feedback> feedback) {
        super("", name, rating, feedback);
        this.netID = "";
        this.name = name;
        this.rating = rating;
        this.feedback = feedback;
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
        return super.equals(o) && getClass() == o.getClass();
    }

    /**
     * HashCode method - It generates a hashCode to company.
     *
     * @return a hashCode for each company
     */

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedback);
    }

    /**
     * toString method.
     *
     * @return a human friendly representation of the company
     */

    @Override
    public String toString() {
        return "Company{"
                + ", name='" + name + '\''
                + ", rating=" + rating
                + ", feedback=" + feedback
                + '}';
    }
}
