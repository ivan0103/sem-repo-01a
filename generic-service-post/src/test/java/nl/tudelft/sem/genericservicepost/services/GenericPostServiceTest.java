package nl.tudelft.sem.genericservicepost.services;


import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.Student;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GenericPostServiceTest  {
    transient GenericPost genericPost;
    transient GenericPost genericPost1;

    @Autowired
    transient GenericPostRepository postRepository;

    @Autowired
    transient GenericPostService genericPostService;

    @BeforeEach
    void setup(){
        genericPost = new GenericPost();
        genericPost1 = new GenericPost();

        genericPost.setId((long) 0);
        genericPost1.setId((long) 1);

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
    void createGenericPost(){
        GenericPost post = genericPostService.createGenericPost(genericPost);
        assertThat(post).isEqualTo(genericPost);
    }

    @Test
    void editGenericPost(){
        GenericPost tmp = genericPostService.createGenericPost(genericPost);

        tmp.setDuration(3);

        GenericPost edited = genericPostService.editGenericPost(tmp);

        GenericPost retrieved = postRepository.getGenericPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);
        assertThat(edited.getDuration()).isEqualTo(3);
    }

    @Test
    void retrieveStudentsInPostTest(){

        Set<Student> students = new HashSet<>();
        Set<Student> students1 = new HashSet<>();
        Student ivan = new Student(1L, "IvanV", genericPost);
        Student marie = new Student(2L, "Marie", genericPost1);
        Student todor = new Student(3L, "Todor", genericPost);
        Student tudor = new Student(4L, "Tudor", genericPost1);


        // Test for students in post 1.
        students.add(ivan);
        students.add(marie);
        students.add(todor);
        students.add(tudor);

        Set<Student> result = new HashSet<>();
        result.add(ivan);
        result.add(todor);

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        tmp.setStudentOfferSet(students);
        students = genericPostService.retrieveStudentsInPost(tmp);

        assertThat(result).isEqualTo(students);

        // Test for students in post 2.
        students1.add(ivan);
        students1.add(marie);
        students1.add(todor);
        students1.add(tudor);

        Set<Student> result1 = new HashSet<>();
        result1.add(marie);
        result1.add(tudor);

        GenericPost tmp1 = genericPostService.createGenericPost(genericPost1);
        tmp1.setStudentOfferSet(students1);
        students1 = genericPostService.retrieveStudentsInPost(tmp1);

        assertThat(result1).isEqualTo(students1);
    }
}