package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "requirement_id")
    private Long id;

    @Column(name = "requirement")
    @NotNull(message = "Requirement cannot be null!")
    @Size(min = 2, max = 20, message = "Requirements must b between 2-20 chars long!")
    private String requirementString;

    @JsonIgnore
    @ManyToMany(mappedBy = "requirementsSet")
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();

    public Requirement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequirementString() {
        return requirementString;
    }

    public void setRequirementString(String requirementString) {
        this.requirementString = requirementString;
    }

    public Set<CompanyOffer> getCompanyOfferSet() {
        return companyOfferSet;
    }

    public void setCompanyOfferSet(Set<CompanyOffer> companyOfferSet) {
        this.companyOfferSet = companyOfferSet;
    }

    @Override
    public String toString() {
        return "Requirement{"
                + "requirementString='" + requirementString + '\''
                + '}';
    }
}
