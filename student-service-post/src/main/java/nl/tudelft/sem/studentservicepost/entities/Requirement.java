package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Requirement {

    @Id
    @Column(name = "requirement")
    @NotNull(message = "Requirement cannot be null!")
    @Size(min = 2, max = 20, message = "Requirements must b between 2-20 chars long!")
    private String requirementString;

    @JsonIgnore
    @ManyToMany(mappedBy = "requirementsSet", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();

    public Requirement() {
    }

    public Requirement(String requirementString) {
        this.requirementString = requirementString;
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
