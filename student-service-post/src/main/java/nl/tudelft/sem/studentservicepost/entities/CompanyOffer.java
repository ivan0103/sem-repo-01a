package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EnableTransactionManagement
@Table(name = "offers")
public class CompanyOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @NotNull(message = "Company ID cannot be null!")
    @Size(min = 4, max = 24, message = "Company ID must be between 4-24 characters!")
    @Column(name = "company_id")
    private String companyId;

    @NotEmpty(message = "At least 1 requirement must be provided!")
    @Valid
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "offer_requirement",
        joinColumns = {@JoinColumn(name = "offer_id")},
        inverseJoinColumns = {@JoinColumn(name = "requirement")}
    )
    private Set<Requirement> requirementsSet = new java.util.LinkedHashSet<>();

    @NotNull(message = "Minimum weekly hours cannot be null!")
    @Min(value = 0, message = "Minimum weekly hours must be cannot be negative!")
    @Column(name = "weekly_hours")
    private Integer weeklyHours;

    @NotNull(message = "Price per hour cannot be null!")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price per hour must be a decimal >= 0!")
    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @NotNull(message = "Total hours cannot be null!")
    @Min(value = 1, message = "Total hours must be > 0!")
    @Column(name = "total_hours")
    private Integer totalHours;

    @NotEmpty(message = "At least 1 expertise must be provided!")
    @Valid
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "offer_expertise",
        joinColumns = {@JoinColumn(name = "offer_id")},
        inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertise = new HashSet<>();

    // Post ID to be linked to is provided in the query parameter
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ChangedOffer> changedOffers = new HashSet<>();

    public CompanyOffer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Set<Requirement> getRequirementsSet() {
        return requirementsSet;
    }

    public void setRequirementsSet(Set<Requirement> requirementsSet) {
        this.requirementsSet = requirementsSet;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Set<Expertise> getExpertise() {
        return expertise;
    }

    public void setExpertise(Set<Expertise> expertise) {
        this.expertise = expertise;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Set<ChangedOffer> getChangedOffers() {
        return changedOffers;
    }

    public void setChangedOffers(
        Set<ChangedOffer> changedOffers) {
        this.changedOffers = changedOffers;
    }

    @Override
    public String toString() {
        return "CompanyOffer{"
            + "id=" + id
            + ", companyId='" + companyId + '\''
            + ", requirementsSet=" + requirementsSet
            + ", weeklyHours=" + weeklyHours
            + ", totalHours=" + totalHours
            + ", expertise=" + expertise
            + ", post=" + post
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyOffer that = (CompanyOffer) o;
        return Objects.equals(companyId, that.companyId)
            && Objects.equals(requirementsSet, that.requirementsSet)
            && Objects.equals(weeklyHours, that.weeklyHours)
            && Objects.equals(totalHours, that.totalHours)
            && Objects.equals(expertise, that.expertise)
            && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, requirementsSet, weeklyHours, totalHours, expertise, post);
    }
}
