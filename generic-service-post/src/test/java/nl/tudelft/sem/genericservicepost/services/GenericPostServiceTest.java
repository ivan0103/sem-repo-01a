package nl.tudelft.sem.genericservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
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
    void createGenericPost() {
        GenericPost post = genericPostService.createGenericPost(genericPost);
        assertThat(post).isEqualTo(genericPost);
    }

    @Test
    void editGenericPost() {
        GenericPost tmp = genericPostService.createGenericPost(genericPost);

        tmp.setDuration(3);

        GenericPost edited = genericPostService.editGenericPost(tmp);

        GenericPost retrieved = postRepository.getGenericPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);
        assertThat(edited.getDuration()).isEqualTo(3);
    }

    @Test
    void retrieveStudentsInPostTest() {

        Set<StudentOffer> studentOffers = new HashSet<>();
        StudentOffer ivan = new StudentOffer(1L, "IvanV", genericPost);
        StudentOffer todor = new StudentOffer(3L, "Todor", genericPost);


        // Test for students in post 1.
        studentOffers.add(ivan);
        studentOffers.add(todor);

        Set<StudentOffer> result = new HashSet<>();
        result.add(ivan);
        result.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentOfferSet(studentOffers);
        studentOffers = genericPostService.retrieveStudentsInPost(tmp);

        assertThat(result).isEqualTo(studentOffers);

        // Test for students in post 2.
        Set<StudentOffer> students1 = new HashSet<>();
        StudentOffer marie = new StudentOffer(2L, "Marie", genericPost1);
        StudentOffer tudor = new StudentOffer(4L, "Tudor", genericPost1);
        students1.add(marie);
        students1.add(tudor);

        Set<StudentOffer> result1 = new HashSet<>();
        result1.add(marie);
        result1.add(tudor);

        GenericPost tmp1 = genericPostService.createGenericPost(genericPost1);
        tmp1.setStudentOfferSet(students1);
        students1 = genericPostService.retrieveStudentsInPost(tmp1);

        assertThat(result1).isEqualTo(students1);

        // Test for exception
        GenericPost genericPost2 = new GenericPost();
        genericPost2.setId(10L);
        Set<StudentOffer> input = new HashSet<>();
        input.add(ivan);
        genericPost2.setStudentOfferSet(input);
        assertThatThrownBy(() -> genericPostService.retrieveStudentsInPost(genericPost2))
            .isInstanceOf(GenericPostNotFoundException.class);
    }

}