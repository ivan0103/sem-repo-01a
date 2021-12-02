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
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "expertise_id")
    private Long id;

    @Column(name = "expertise")
    @NotNull
    @Size(min = 2, max = 20)
    private String expertiseString;

    @JsonIgnore
    @ManyToMany(mappedBy = "expertiseSet", fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Post> postSet = new HashSet<>();

    public Expertise() {
    }

    public Expertise(String string) {
        this.expertiseString = string;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expertise expertise = (Expertise) o;
        return Objects.equals(expertiseString, expertise.expertiseString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertiseString);
    }
}