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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    @Column(name = "company_id")
    private String companyId;

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
