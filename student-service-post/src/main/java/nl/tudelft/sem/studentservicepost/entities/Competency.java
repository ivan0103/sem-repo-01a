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
public class Competency {

    @Id
    @Column(name = "competency")
    @NotNull
    @Size(max = 20)
    private String competencyString;

    @JsonIgnore
    @ManyToMany(mappedBy = "competencySet", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Post> postSet = new HashSet<>();

    @Column(name = "searchable_string")
    @Size(max = 20)
    @JsonIgnore
    private String searchableString;

    public Competency() {
    }

    public Competency(String string) {
        this.competencyString = string;
        this.searchableString = makeSearchable(string);
    }

    public String getCompetencyString() {
        return competencyString;
    }

    public void setCompetencyString(String competencyString) {
        this.competencyString = competencyString;
        this.searchableString = makeSearchable(competencyString);
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
    }

    public String getSearchableString() {
        return searchableString;
    }

    public void setSearchableString(String searchableString) {
        this.searchableString = makeSearchable(searchableString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Competency that = (Competency) o;
        return this.searchableString.equalsIgnoreCase(that.searchableString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competencyString);
    }

    @Override
    public String toString() {
        return competencyString;
    }

    public static String makeSearchable(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("\\s", "");
    }


}
