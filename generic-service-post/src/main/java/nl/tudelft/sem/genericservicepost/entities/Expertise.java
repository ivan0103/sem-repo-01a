package nl.tudelft.sem.genericservicepost.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Expertise {
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
}
