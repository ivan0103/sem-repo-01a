package nl.tudelft.sem.genericservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.StudentOfferNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.processing.Generated;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void retrieveStudentsInPostTest(){

        Set<StudentOffer> studentOffers = new HashSet<>();
        Set<StudentOffer> studentsOffers1 = new HashSet<>();
        StudentOffer ivan = new StudentOffer(1L, "Ivan", genericPost);
        StudentOffer marie = new StudentOffer(2L, "Marie", genericPost1);
        StudentOffer todor = new StudentOffer(3L, "Todor", genericPost);
        StudentOffer tudor = new StudentOffer(4L, "Tudor", genericPost1);


        // Test for students in post 1.
        studentOffers.add(ivan);
        studentOffers.add(todor);
        studentsOffers1.add(marie);
        studentsOffers1.add(tudor);

        Set<StudentOffer> result = new HashSet<>();
        result.add(ivan);
        result.add(todor);
        Set<StudentOffer> result1 = new HashSet<>();
        result1.add(marie);
        result1.add(tudor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentOfferSet(studentOffers);
        studentOffers = genericPostService.retrieveStudentsInPost(tmp);
        GenericPost tmp1 = genericPostService.createGenericPost(genericPost1);
        tmp1.setStudentOfferSet(studentsOffers1);
        studentsOffers1 = genericPostService.retrieveStudentsInPost(tmp1);

        assertThat(result).isEqualTo(studentOffers);
        assertThat(result1).isEqualTo(studentsOffers1);
    }
    @Test
    void retrieveStudentsInPostExceptionTest(){
        StudentOffer marie = new StudentOffer(1L, "Marie", genericPost);
        GenericPost genericPost2 = new GenericPost();
        genericPost2.setId(10L);
        Set<StudentOffer> input = new HashSet<>();
        input.add(marie);
        genericPost2.setStudentOfferSet(input);
        assertThatThrownBy(() -> genericPostService.retrieveStudentsInPost(genericPost2))
                .isInstanceOf(GenericPostNotFoundException.class);
    }

    @Test
    void setSelectedStudentTest(){
        Set<StudentOffer> studentOffers = new HashSet<>();
        StudentOffer tudor = new StudentOffer(1L, "Tudor", genericPost);
        StudentOffer todor = new StudentOffer(3L, "Todor", genericPost);

        studentOffers.add(tudor);
        studentOffers.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentOfferSet(studentOffers);
        StudentOffer output = genericPostService.setSelectedStudent(tudor, tmp);

        assertThat(output).isEqualTo(tudor);
        assertThat(tmp.getSelectedStudentOffer()).isEqualTo(tudor);
    }

    @Test
    void setSelectedStudentNoPostTest(){
        GenericPost genericPost2 = new GenericPost();
        genericPost2.setId(10L);
        assertThatThrownBy(() -> genericPostService.setSelectedStudent(new StudentOffer(1L, "Ivan", genericPost), genericPost2))
                .isInstanceOf(GenericPostNotFoundException.class);
    }

    @Test
    void setSelectedStudentNoStudentOfferTest(){
        StudentOffer todor = new StudentOffer(3L, "Todor", genericPost);
        Set<StudentOffer> studentOffers = new HashSet<>();
        studentOffers.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentOfferSet(studentOffers);

        assertThatThrownBy(() -> genericPostService.setSelectedStudent(new StudentOffer(1L, "Ivan", genericPost), tmp))
                .isInstanceOf(StudentOfferNotFoundException.class);
    }
}