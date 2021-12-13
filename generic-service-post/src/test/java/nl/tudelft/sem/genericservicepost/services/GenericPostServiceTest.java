package nl.tudelft.sem.genericservicepost.services;


import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GenericPostServiceTest  {
    transient GenericPost genericPost;
    transient GenericPost genericPost1;

    @Autowired
    transient PostRepository postRepository;

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

        genericPost.getExpertiseSet().add(new Expertise("A"));
        genericPost1.getExpertiseSet().add(new Expertise("A"));
    }

    @Test
    void createGenericPost(){
        GenericPost post = genericPostService.createGenericPost(genericPost);
        assertThat(post).isEqualTo(genericPost);
    }

}