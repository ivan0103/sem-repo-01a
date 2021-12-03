package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Collection;
import javax.transaction.Transactional;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class PostServiceTest {

    transient Post post;
    transient Post post1;


    @Autowired
    transient PostService postService;

    @Autowired
    transient PostRepository postRepository;

    @Autowired
    transient CompetencyRepository competencyRepository;

    @Autowired
    transient ExpertiseRepository expertiseRepository;


    @BeforeEach
    void setup() {
        post = new Post();
        post.setId(0L);
        post.setAuthor("despacito");
        post.setPricePerHour(new BigDecimal("15.00"));
        post.getCompetencySet().add(new Competency("comp1"));
        post.getExpertiseSet().add(new Expertise("exp1"));

        post1 = new Post();
        post1.setId(12L);
        post1.setAuthor("lillolallo");
        post1.setPricePerHour(new BigDecimal("100.00"));
        post1.getCompetencySet().add(new Competency("comp2"));
        post1.getExpertiseSet().add(new Expertise("exp1"));

    }

    //@AfterEach
    void reset() {
        postRepository.deleteAllInBatch();
        competencyRepository.deleteAllInBatch();
        expertiseRepository.deleteAllInBatch();
    }

    @Test
    void createPost() {
        Post tmp = postService.createPost(post);
        assertThat(tmp).isEqualTo(post);
    }

    @Test
    void createPostSameExpertise() {
        Post tmp2 = postService.createPost(post1);
        assertThat(tmp2).isEqualTo(post1);
    }

    @Test
    // TODO fix this
    void editPost() {

        Post tmp = postService.createPost(post);
        postRepository.flush();
        Competency newCompetency = new Competency("comp2");

        tmp.setPricePerHour(new BigDecimal("42.00"));
        tmp.getCompetencySet().add(newCompetency);

        Post edited = postService.editPost(tmp);

        Post retrieved = postRepository.getPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);

        assertThat(edited.getPricePerHour()).isEqualTo(new BigDecimal("42.00"));

        assertThat(edited.getCompetencySet()).hasSize(2).containsOnlyOnce(newCompetency);

    }

    @Test
    void editNonExistentPost() {
        Long realId = postService.createPost(post).getId();

        Post toEdit = new Post();
        toEdit.setId(realId + 100L); // set id to something different from existing post

        assertThatThrownBy(() -> postService.editPost(toEdit))
            .isInstanceOf(PostNotFoundException.class);
        postRepository.delete(toEdit);

    }

    @Test
    // TODO fix this
    void editPostFailingNetId() {
        Post tmp = postService.createPost(post);
        Competency newCompetency = new Competency("comp2");

        tmp.setPricePerHour(new BigDecimal("42.00"));
        tmp.getCompetencySet().add(newCompetency);

        tmp.setAuthor("anotherguy");

        assertThatThrownBy(() -> postService.editPost(tmp))
            .isInstanceOf(InvalidEditException.class);

    }

    @Test
    void testSearchByKeywordFound() {
        Post tmp = postService.createPost(post);
        Collection<Post> result = postService.searchByKeyword("cOmp  1");
        assertThat(result).containsOnlyOnce(tmp).hasSize(1);
    }

    @Test
    void testSearchByKeywordNotFound() {
        Post tmp = postService.createPost(post);
        Post tmp2 = postService.createPost(post1);
        Collection<Post> result = postService.searchByKeyword("cOmp  4");
        assertThat(result).isEmpty();
    }

    @Test
    void testAll() {
        Post tmp = postService.createPost(post);
        Post tmp2 = postService.createPost(post1);
        Collection<Post> result = postService.getAll();
        assertThat(result).contains(tmp).contains(tmp2).hasSize(2);

    }
}