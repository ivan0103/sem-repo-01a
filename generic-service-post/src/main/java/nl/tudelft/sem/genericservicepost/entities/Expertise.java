package nl.tudelft.sem.genericservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

@Entity
public class Expertise {

    public Expertise() {
    }

    public Expertise(String string) {
        this.expertiseString = string;
    }

    @Id
    @Column(name = "expertise")
    @NotNull
    @Size(min = 2, max = 20)
    private String expertiseString;

    @JsonIgnore
    @ManyToMany(mappedBy = "expertiseSet", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<GenericPost> genericPostSet = new HashSet<>();

    public String getExpertiseString() {
        return expertiseString;
    }

    public void setExpertiseString(String expertiseString) {
        this.expertiseString = expertiseString;
    }

    public Set<GenericPost> getGenericPostSet() {
        return genericPostSet;
    }

    public void setGenericPostSet(Set<GenericPost> genericPostSet) {
        this.genericPostSet = genericPostSet;
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
        return Objects.hash(getExpertiseString());
    }
}
