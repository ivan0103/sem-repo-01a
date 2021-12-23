package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.web.client.RestTemplateBuilder;


// Validation docs: https://javaee.github.io/javaee-spec/javadocs/javax/validation/constraints/package-summary.html

/**
 * The type Post.
 */
@JsonFilter("postFilter")
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "id")
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
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "post_expertise",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Expertise> expertiseSet = new HashSet<>();

    @NotEmpty(message = "At least 1 competency must be provided!")
    @Valid
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "post_competency",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "competency")}
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Competency> competencySet = new HashSet<>();

    // Prevent company offer spoofing when creating a new post
    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CompanyOffer> companyOfferSet = new HashSet<>();

    // Represents remote object in Users microservice
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private transient User user;


    /**
     * Instantiates a new Post.
     */
    public Post() {
    }

    protected Post(String author) {
        this.author = author;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
        // We don't have access to the author NetID until the deserializer
        // gets to setting the author so we can only initialise the user now
        this.user = new UserImplProxy(author, new RestTemplateBuilder());
    }

    /**
     * Gets price per hour.
     *
     * @return the price per hour
     */
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    /**
     * Sets price per hour.
     *
     * @param pricePerHour the price per hour
     */
    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    /**
     * Gets expertise set.
     *
     * @return the expertise set
     */
    public Set<Expertise> getExpertiseSet() {
        return expertiseSet;
    }

    /**
     * Sets expertise set.
     *
     * @param expertiseSet the expertise set
     */
    public void setExpertiseSet(Set<Expertise> expertiseSet) {
        this.expertiseSet = expertiseSet;
    }

    /**
     * Gets competency set.
     *
     * @return the competency set
     */
    public Set<Competency> getCompetencySet() {
        return competencySet;
    }

    /**
     * Sets competency set.
     *
     * @param competencySet the competency set
     */
    public void setCompetencySet(Set<Competency> competencySet) {
        this.competencySet = competencySet;
    }

    /**
     * Gets company offer set.
     *
     * @return the company offer set
     */
    public Set<CompanyOffer> getCompanyOfferSet() {
        return companyOfferSet;
    }

    /**
     * Sets company offer set.
     *
     * @param companyOfferSet the company offer set
     */
    public void setCompanyOfferSet(Set<CompanyOffer> companyOfferSet) {
        this.companyOfferSet = companyOfferSet;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        if (this.user == null) {
            this.user = new UserImplProxy(this.author, new RestTemplateBuilder());
        }
        return this.user;
    }

    @Override
    public String toString() {
        return "Post{"
            + "id=" + id
            + ", author='" + author + '\''
            + ", pricePerHour=" + pricePerHour
            + ", expertiseSet=" + expertiseSet
            + ", competencySet=" + competencySet
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
            && Objects.equals(competencySet, post.competencySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, pricePerHour, expertiseSet, competencySet);
    }
}
