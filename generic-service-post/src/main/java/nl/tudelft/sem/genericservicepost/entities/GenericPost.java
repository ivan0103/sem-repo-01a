package nl.tudelft.sem.genericservicepost.entities;

import org.h2.engine.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "generic_posts")
public class GenericPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generic_post_id")
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
