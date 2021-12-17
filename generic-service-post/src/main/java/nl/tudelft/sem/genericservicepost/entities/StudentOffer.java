package nl.tudelft.sem.genericservicepost.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentOffer that = (StudentOffer) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getStudentId(), that.getStudentId())
                && Objects.equals(getGenericPost(), that.getGenericPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStudentId(), getGenericPost());
    }
}
