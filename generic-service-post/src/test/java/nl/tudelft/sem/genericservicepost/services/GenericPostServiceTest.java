package nl.tudelft.sem.genericservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.entities.Student;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenericPostServiceTest {
    transient GenericPost genericPost;
    transient GenericPost genericPost1;

    @Autowired
    transient GenericPostRepository postRepository;

    @Autowired
    transient GenericPostService genericPostService;

    @BeforeEach
    void setup() {
        genericPost = new GenericPost();
        genericPost1 = new GenericPost();

        genericPost.setId(0L);
        genericPost1.setId(1L);

        genericPost.setAuthor("The Rock");
        genericPost1.setAuthor("The Wok");

        genericPost.setHoursPerWeek(15);
        genericPost1.setHoursPerWeek(3);

        genericPost.setDuration(5);
        genericPost1.setDuration(1);

        genericPost.getExpertiseSet().add(new Expertise("ABC"));
        genericPost1.getExpertiseSet().add(new Expertise("ABC"));
    }

    @Test
    void createGenericPostTest() {
        GenericPost post = genericPostService.createGenericPost(genericPost);
        assertThat(post).isEqualTo(genericPost);
    }

    @Test
    void editGenericPostTest() {
        GenericPost tmp = genericPostService.createGenericPost(genericPost);

        tmp.setDuration(3);

        GenericPost edited = genericPostService.editGenericPost(tmp);

        GenericPost retrieved = postRepository.getGenericPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);
        assertThat(edited.getDuration()).isEqualTo(3);
    }

    @Test
    void editGenericPostExceptionTest() {
        GenericPost tmp = new GenericPost();
        tmp.setId(10L);
        assertThatThrownBy(() -> genericPostService.editGenericPost(tmp))
            .isInstanceOf(GenericPostNotFoundException.class);
    }

    @Test
    void retrieveStudentsInPostTest() {
        Set<Student> students= new HashSet<>();
        Student ivan = new Student("1L", "Ivan", 3.5F);
        Student todor = new Student("3L", "Todor", 3.7F);

        students.add(ivan);
        students.add(todor);

        Set<Student> result = new HashSet<>();
        result.add(ivan);
        result.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentSet(students);
        students = genericPostService.retrieveStudentsInPost(tmp);

        assertThat(result).isEqualTo(students);
    }

    @Test
    void retrieveStudentsInPostExceptionTest() {
        Student todor = new Student("3L", "Todor", 3.7F);
        GenericPost genericPost2 = new GenericPost();
        genericPost2.setId(10L);
        Set<Student> input = new HashSet<>();
        input.add(todor);
        genericPost2.setStudentSet(input);
        assertThatThrownBy(() -> genericPostService.retrieveStudentsInPost(genericPost2))
            .isInstanceOf(GenericPostNotFoundException.class);
    }

    @Test
    void retrieveEmptyStudentsInPostTest() {
        Set<Student> students = new HashSet<>();
        Set<Student> result = new HashSet<>();
        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentSet(students);
        students = genericPostService.retrieveStudentsInPost(tmp);
        assertThat(result).isEqualTo(students);
    }

    @Test
    void setSelectedStudentTest() {
        Set<Student> students = new HashSet<>();
        Student tudor = new Student("1L", "Tudor", 3.5F);
        Student todor = new Student("3L", "Todor", 3.6F);

        students.add(tudor);
        students.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentSet(students);
        Student output = genericPostService.setSelectedStudent(tudor, tmp);

        assertThat(output).isEqualTo(tudor);
        assertThat(tmp.getSelectedStudent()).isEqualTo(tudor);
    }

    @Test
    void setSelectedStudentNoPostTest() {
        GenericPost genericPost2 = new GenericPost();
        genericPost2.setId(10L);
        assertThatThrownBy(
            () -> genericPostService.setSelectedStudent(new Student("1L", "Ivan", 3.5F),
                genericPost2))
            .isInstanceOf(GenericPostNotFoundException.class);
    }
}