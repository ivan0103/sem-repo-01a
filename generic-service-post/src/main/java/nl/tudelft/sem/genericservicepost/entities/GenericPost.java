package nl.tudelft.sem.genericservicepost.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "generic_posts")
public class GenericPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generic_post_id")
    private Long id;

    @Column(name = "author_id")
    private String author;

    @NotNull(message = "Minimum weekly hours cannot be null!")
    @Min(value = 0, message = "Minimum weekly hours cannot be negative!")
    @Max(value = 20, message = "Maximum weekly hours cannot be above 20!")
    @Column(name = "hours_per_week")
    private int hoursPerWeek;

    @NotNull(message = "Duration cannot be null!")
    @Min(value = 1, message = "Duration must be above 1 month!")
    @Max(value = 6, message = "Duration cannot be above 6 months!")
    @Column(name = "duration")
    private int duration;

    @NotEmpty(message = "At least 1 expertise must be provided!")
    @Valid
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "generic_post_expertise",
        joinColumns = {@JoinColumn(name = "generic_post_id")},
        inverseJoinColumns = {@JoinColumn(name = "expertise")}
    )
    private Set<Expertise> expertiseSet = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private transient Set<StudentOffer> studentOfferOfferSet = new HashSet<>();

    @OneToOne
    @Column(name = "candidate")
    transient private StudentOffer selectedStudentOffer = new StudentOffer();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Expertise> getExpertiseSet() {
        return expertiseSet;
    }

    public void setExpertiseSet(Set<Expertise> expertiseSet) {
        this.expertiseSet = expertiseSet;
    }

    public Set<StudentOffer> getStudentOfferSet() {
        return studentOfferOfferSet;
    }

    public void setStudentOfferSet(Set<StudentOffer> studentOfferOfferSet) {
        this.studentOfferOfferSet = studentOfferOfferSet;
    }

    public StudentOffer getSelectedStudentOffer() { return selectedStudentOffer; }

    public void setSelectedStudentOffer(StudentOffer studentOffer){ this.selectedStudentOffer = studentOffer; }

    @Override
    public String toString() {
        return "GenericPost{"
            + "id=" + id
            + ", author='"
            + author + '\''
            + ", hoursPerWeek="
            + hoursPerWeek + ", duration="
            + duration + ", expertiseSet="
            + expertiseSet + ", studentOfferSet="
            + studentOfferOfferSet + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericPost that = (GenericPost) o;
        return hoursPerWeek == that.hoursPerWeek
                && duration == that.duration
                && id.equals(that.id)
                && author.equals(that.author)
                && expertiseSet.equals(that.expertiseSet)
                && studentOfferOfferSet.equals(that.studentOfferOfferSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                author,
                hoursPerWeek,
                duration,
                expertiseSet,
                studentOfferOfferSet,
                selectedStudentOffer);
    }
}
