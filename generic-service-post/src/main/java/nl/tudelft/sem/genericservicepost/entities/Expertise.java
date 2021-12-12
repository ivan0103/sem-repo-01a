package nl.tudelft.sem.genericservicepost.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Expertise {

    protected Expertise() {
    }

    protected Expertise(String string) {
        this.expertiseString = string;
    }

    @Id
    @Column(name = "expertise")
    private String expertiseString;

    @ManyToMany(mappedBy = "expertiseSet")
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
        return Objects.hash(getExpertiseString(), getGenericPostSet());
    }
}
