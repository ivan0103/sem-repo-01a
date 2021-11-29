package nl.tudelft.sem.genericservicepost.entities;

import javax.persistence.*;

@Entity
public class StudentOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "generic_post_id", referencedColumnName = "generic_post_id")
    private GenericPost genericPost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
