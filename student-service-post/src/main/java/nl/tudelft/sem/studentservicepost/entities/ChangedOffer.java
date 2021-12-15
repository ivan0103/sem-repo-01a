package nl.tudelft.sem.studentservicepost.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Changed offer.
 * This type is identical to normal offers, but has a many-to-1 to offers
 */
@Entity
@EnableTransactionManagement
public class ChangedOffer extends CompanyOffer {

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnore
    private CompanyOffer parent;


    public CompanyOffer getParent() {
        return parent;
    }

    public void setParent(CompanyOffer parent) {
        this.parent = parent;
    }


    public ChangedOffer() {
    }

    /**
     * Instantiates a new Changed offer based on a parent offer.
     *
     * @param parent the parent
     */
    public ChangedOffer(CompanyOffer parent) {
        this.parent = parent;

        this.setCompanyId(parent.getCompanyId());
        this.setExpertise(parent.getExpertise());
        this.setPost(parent.getPost());
        this.setRequirementsSet(parent.getRequirementsSet());
        this.setTotalHours(parent.getTotalHours());
        this.setWeeklyHours(parent.getWeeklyHours());
        this.setPricePerHour(parent.getPricePerHour());
    }
}
