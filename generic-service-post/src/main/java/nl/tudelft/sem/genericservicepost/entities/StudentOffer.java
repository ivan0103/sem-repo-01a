package nl.tudelft.sem.genericservicepost.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StudentOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @NotNull(message = "Student ID cannot be null!")
    @Size(min = 4, max = 24, message = "Student ID must be between 4-24 characters!")
    @Column(name = "student_id")
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "generic_post_id", referencedColumnName = "generic_post_id")
    private GenericPost genericPost;

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
}
