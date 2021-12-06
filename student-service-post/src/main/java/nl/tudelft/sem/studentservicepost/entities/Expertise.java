package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Expertise {

    @Id
    @Column(name = "expertise")
    @NotNull
    @Size(min = 2, max = 20)
    private String expertiseString;

    @JsonIgnore
    @ManyToMany(mappedBy = "expertiseSet", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Post> postSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "expertise", fetch = FetchType.EAGER)
    private Set<CompanyOffer> offerSet = new HashSet<>();

    @Column(name = "searchable_string")
    @Size(max = 20)
    private String searchableString;

    public Expertise() {
    }

    public Expertise(String string) {
        this.expertiseString = string;
        this.searchableString = Competency.makeSearchable(string);
    }

    public String getExpertiseString() {
        return expertiseString;
    }

    public void setExpertiseString(String expertiseString) {
        this.expertiseString = expertiseString;
        this.searchableString = Competency.makeSearchable(expertiseString);
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
    }

    @Override
    public String toString() {
        return expertiseString;
    }

    public Set<CompanyOffer> getOfferSet() {
        return offerSet;
    }

    public void setOfferSet(Set<CompanyOffer> offerSet) {
        this.offerSet = offerSet;
    }

    public String getSearchableString() {
        return searchableString;
    }

    public void setSearchableString(String searchableString) {
        this.searchableString = Competency.makeSearchable(searchableString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expertise expertise = (Expertise) o;
        String thatE = Competency.makeSearchable(expertise.expertiseString);
        String thisE = Competency.makeSearchable(this.expertiseString);
        return thisE.equalsIgnoreCase(thatE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertiseString);
    }
}