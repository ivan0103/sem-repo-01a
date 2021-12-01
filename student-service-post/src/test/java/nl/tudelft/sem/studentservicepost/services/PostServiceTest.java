package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    transient Post post;

    @Autowired
    transient PostService postService;

    @BeforeEach
    void setup() {
        post = new Post();
        post.setId(0L);
        post.setAuthor("despacito");
        post.setPricePerHour(new BigDecimal("15.0"));
        post.getCompetencySet().add(new Competency("comp1"));
        post.getExpertiseSet().add(new Expertise("exp1"));
    }

    @Test
    void createPost() {
        Post tmp = postService.createPost(post);
        assertThat(tmp).isEqualTo(post);
    }
}