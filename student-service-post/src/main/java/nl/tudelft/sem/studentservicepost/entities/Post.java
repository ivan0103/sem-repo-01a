package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
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
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// Validation docs: https://javaee.github.io/javaee-spec/javadocs/javax/validation/constraints/package-summary.html

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "post_id")
    private Long id;

    // NetID length assumed to be between 4 and 24 chars long
    @NotNull(message = "NetID cannot be null!")
    @Size(min = 4, max = 24, message = "NetID must be between 4-24 characters!")
    @Column(name = "author_id")
    private String author;

    @NotNull(message = "Price per hour cannot be null!")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price per hour must be a decimal >= 0!")
    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @NotEmpty(message = "At least 1 expertise must be provided!")
    @Valid
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "post_expertise",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertiseSet = new HashSet<>();

    @NotEmpty(message = "At least 1 competency must be provided!")
    @Valid
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "post_competency",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "competency")}
    )
    private Set<Competency> competencySet = new HashSet<>();

    // Prevent company offer spoofing when creating a new post
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "post")
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();


    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Set<Expertise> getExpertiseSet() {
        return expertiseSet;
    }

    public void setExpertiseSet(Set<Expertise> expertiseSet) {
        this.expertiseSet = expertiseSet;
    }

    public Set<Competency> getCompetencySet() {
        return competencySet;
    }

    public void setCompetencySet(Set<Competency> competencySet) {
        this.competencySet = competencySet;
    }

    public Set<CompanyOffer> getCompanyOfferSet() {
        return companyOfferSet;
    }

    public void setCompanyOfferSet(Set<CompanyOffer> companyOfferSet) {
        this.companyOfferSet = companyOfferSet;
    }

    @Override
    public String toString() {
        return "Post{"
            + "id=" + id
            + ", author='" + author + '\''
            + ", pricePerHour=" + pricePerHour
            + ", expertiseSet=" + expertiseSet
            + ", competencySet=" + competencySet
            + ", companyOfferSet=" + companyOfferSet
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
        Post post = (Post) o;
        return Objects.equals(author, post.author)
            && Objects.equals(pricePerHour, post.pricePerHour)
            && Objects.equals(expertiseSet, post.expertiseSet)
            && Objects.equals(competencySet, post.competencySet)
            && Objects.equals(companyOfferSet, post.companyOfferSet);
    }

}
