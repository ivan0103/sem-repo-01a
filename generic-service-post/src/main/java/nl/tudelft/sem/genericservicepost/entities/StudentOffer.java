package nl.tudelft.sem.genericservicepost.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class StudentOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private long id;

    @OneToOne
    @Column(name = "student_id")
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "generic_post_id", referencedColumnName = "generic_post_id")
    private GenericPost genericPost;

}
