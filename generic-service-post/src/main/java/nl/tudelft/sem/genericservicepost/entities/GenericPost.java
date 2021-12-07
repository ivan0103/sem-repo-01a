package nl.tudelft.sem.genericservicepost.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "generic_posts")
public class GenericPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generic_post_id")
    private Long id;

    @NotNull(message = "NetID cannot be null!")
    @Size(min = 4, max = 24, message = "NetID must be between 4-24 characters!")
    @Column(name = "author_id")
    private String author;

    @Column(name = "hours_per_week")
    private int hoursPerWeek;

    @Column(name = "duration")
    private int duration;

    @ManyToMany
    @JoinTable(
            name = "generic_post_expertise",
            joinColumns = {@JoinColumn(name = "generic_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertiseSet = new HashSet<>();

    @OneToMany
    private Set<StudentOffer> studentOfferSet = new HashSet<>();

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", author='" + author + '\''
                + ", hoursPerWeek=" + hoursPerWeek
                + ", duration=" + duration
                + ", expertiseSet=" + expertiseSet
                + ", studentOfferSet=" + studentOfferSet
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericPost that = (GenericPost) o;
        return getId().equals(that.getId())
                && getHoursPerWeek() == that.getHoursPerWeek()
                && getDuration() == that.getDuration()
                && getAuthor().equals(that.getAuthor())
                && Objects.equals(getExpertiseSet(), that.getExpertiseSet())
                && Objects.equals(getStudentOfferSet(), that.getStudentOfferSet());
    }
}
