package nl.tudelft.sem.studentservicepost.entities;

import lombok.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EnableTransactionManagement
@Table(name = "offers")
public class CompanyOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "offer_requirement",
            joinColumns = {@JoinColumn(name = "offer_id")},
            inverseJoinColumns = {@JoinColumn(name = "requirement")}
    )
    private Set<Requirement> requirementsSet = new java.util.LinkedHashSet<>();

    @Column(name = "weekly_hours")
    private Integer weeklyHours;

    @Column(name = "total_hours")
    private Integer totalHours;

    @ManyToMany
    @JoinTable(
            name = "offer_expertise",
            joinColumns = {@JoinColumn(name = "offer_id")},
            inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertise = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;
}
