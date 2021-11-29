package nl.tudelft.sem.studentservicepost.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {

    @Id
    @Column(name = "requirement")
    private String requirementString;

    @ManyToMany(mappedBy = "requirementsSet")
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();
}
