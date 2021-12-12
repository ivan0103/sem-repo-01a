package nl.tudelft.sem.genericservicepost.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "generic_posts")
public class GenericPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generic_post_id")
    private Long id;

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
        return studentOfferSet;
    }

    public void setStudentOfferSet(Set<StudentOffer> studentOfferSet) {
        this.studentOfferSet = studentOfferSet;
    }

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
                + studentOfferSet + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenericPost that = (GenericPost) o;
        return getHoursPerWeek() == that.getHoursPerWeek()
                && getDuration() == that.getDuration()
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getAuthor(), that.getAuthor())
                && Objects.equals(getExpertiseSet(), that.getExpertiseSet())
                && Objects.equals(getStudentOfferSet(), that.getStudentOfferSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getAuthor(),
                getHoursPerWeek(),
                getDuration(),
                getExpertiseSet(),
                getStudentOfferSet());
    }
}
