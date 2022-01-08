package nl.tudelft.sem.genericservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Entity
@EnableTransactionManagement
@Table(name = "student_offers")
public class StudentOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "offer_id")
    private Long id;

    @NotNull(message = "Student ID cannot be null!")
    @Size(min = 4, max = 24, message = "Student ID must be between 4-24 characters!")
    @Column(name = "student_id")
    private String studentId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "generic_post_id", referencedColumnName = "generic_post_id")
    private GenericPost genericPost;

    public StudentOffer() {

    }

    /**
     * Instantiates a new Student offer.
     *
     * @param id          the id
     * @param studentId   the student id
     * @param genericPost the generic post
     */
    public StudentOffer(Long id, String studentId, GenericPost genericPost) {
        this.id = id;
        this.studentId = studentId;
        this.genericPost = genericPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public GenericPost getGenericPost() {
        return genericPost;
    }

    public void setGenericPost(GenericPost genericPost) {
        this.genericPost = genericPost;
    }

    @Override
    public String toString() {
        return "StudentOffer{"
            + "id=" + id
            + ", studentId='" + studentId + '\''
            + ", genericPost=" + genericPost
            + '}';
    }
}
