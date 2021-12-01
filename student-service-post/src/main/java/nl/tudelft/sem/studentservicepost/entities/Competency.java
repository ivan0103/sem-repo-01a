package nl.tudelft.sem.studentservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @ManyToMany(mappedBy = "competencySet")
    private Set<Post> postSet = new HashSet<>();

    public Competency() {
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
    public String toString() {
        return competencyString;
    }
}
