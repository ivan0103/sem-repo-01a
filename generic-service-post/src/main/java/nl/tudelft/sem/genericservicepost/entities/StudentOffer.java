package nl.tudelft.sem.genericservicepost.entities;

import org.h2.engine.User;

import javax.persistence.*;

@Entity
public class StudentOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private long id;

    @OneToOne
    @Column(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "generic_post_id", referencedColumnName = "generic_post_id")
    private GenericPost genericPost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
}
