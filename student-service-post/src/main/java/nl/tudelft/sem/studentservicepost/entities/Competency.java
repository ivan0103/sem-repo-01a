package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;


@Entity
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "competency_id")
    private Long id;

    @Column(name = "competency")
    @NotNull
    @Size(min = 0, max = 20)
    private String competencyString;

    @JsonIgnore
    @ManyToMany(mappedBy = "competencySet", fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE})
    private Set<Post> postSet = new HashSet<>();

    public Competency() {
    }

    public Competency(String string) {
        this.competencyString = string;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetencyString() {
        return competencyString;
    }

    public void setCompetencyString(String competencyString) {
        this.competencyString = competencyString;
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
        return Objects.equals(competencyString, that.competencyString);
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
