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
public class Expertise {

    @Id
    @Column(name = "expertise")
    @NotNull
    @Size(min = 2, max = 20)
    private String expertiseString;

    @JsonIgnore
    @ManyToMany(mappedBy = "expertiseSet")
    private Set<Post> postSet = new HashSet<>();

    public Expertise() {
    }

    public String getExpertiseString() {
        return expertiseString;
    }

    public void setExpertiseString(String expertiseString) {
        this.expertiseString = expertiseString;
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
}