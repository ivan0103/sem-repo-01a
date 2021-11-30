package nl.tudelft.sem.studentservicepost.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "posts")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "author_id")
    private String author;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @ManyToMany
    @JoinTable(
        name = "post_expertise",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertiseSet = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "post_competency",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "competency")}
    )
    private Set<Competency> competencySet = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();


}
