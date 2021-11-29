package nl.tudelft.sem.studentservicepost.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expertise{

    @Id
    @Column(name = "expertise")
    private String expertiseString;

    @ManyToMany(mappedBy = "expertiseSet")
    private Set<Post> postSet= new HashSet<>();
}