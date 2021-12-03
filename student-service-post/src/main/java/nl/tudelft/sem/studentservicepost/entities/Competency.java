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


@Entity
public class Competency {

    @Id
    @Column(name = "competency")
    @NotNull
    @Size(min = 0, max = 20)
    private String competencyString;

    @JsonIgnore
    @ManyToMany(mappedBy = "competencySet", fetch = FetchType.EAGER)
    private Set<Post> postSet = new HashSet<>();

    public Competency() {
    }

    public Competency(String string) {
        this.competencyString = string.toLowerCase(Locale.ROOT).replaceAll("\\s", "");
    }

    public String getCompetencyString() {
        return competencyString;
    }

    public void setCompetencyString(String competencyString) {
        this.competencyString = competencyString.toLowerCase(Locale.ROOT).replaceAll("\\s", "");
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
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
        String thatC = that.competencyString.replaceAll("\\s", "");
        String thisC = this.competencyString.replaceAll("\\s", "");
        return thisC.equalsIgnoreCase(thatC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competencyString);
    }

    @Override
    public String toString() {
        return competencyString;
    }
}
