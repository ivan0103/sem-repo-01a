package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ServiceRequestPostTest {
    transient GenericPost genericPost;
    transient GenericPost genericPost1;

    @Autowired
    transient PostRepository postRepository;

    @Autowired
    transient ServiceRequestPost serviceRequestPost;

    @BeforeEach
    void setup(){
        genericPost = new GenericPost();
        genericPost1 = new GenericPost();

        genericPost.setId((long) 0);
        genericPost1.setId((long) 1);

        genericPost.setAuthor("The Rock");
        genericPost1.setAuthor("The Wok");

        genericPost.setHoursPerWeek(19);
        genericPost1.setHoursPerWeek(15);

        genericPost.setDuration(5);
        genericPost1.setDuration(1);

        genericPost.getExpertiseSet().add(new Expertise("A"));
        genericPost1.getExpertiseSet().add(new Expertise("A"));
    }

    @Test
    void createGenericPost(){
        GenericPost post = serviceRequestPost.createPost(genericPost);
        assertThat(post).isEqualTo(genericPost);
    }

    @Test
    void createPostSameExpertise(){
        serviceRequestPost.createPost(genericPost);
        GenericPost post = serviceRequestPost.createPost(genericPost1);
        assertThat(post).isEqualTo(genericPost1);
    }

}
