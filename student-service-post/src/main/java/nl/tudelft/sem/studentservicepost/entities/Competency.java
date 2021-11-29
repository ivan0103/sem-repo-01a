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
public class Competency{

    @Id
    @Column(name = "competency")
    private String competency;

    @ManyToMany(mappedBy = "competencySet")
    private Set<Post> postSet= new HashSet<>();
}
