package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    final transient String expertise1 = "exp1";
    final transient String competency1 = "comp1";
    final transient String competency2 = "comp2";


    transient Post post;
    transient Post post1;
    transient Post postReturned;
    transient Post post1Returned;

    transient Competency comp1;
    transient Competency comp2;


    transient Expertise exp1;
    transient Expertise exp2;


    @InjectMocks
    transient PostService postService;

    @Mock
    transient PostRepository postRepository;

    @Mock
    transient CompetencyRepository competencyRepository;

    @Mock
    transient ExpertiseRepository expertiseRepository;


    @BeforeEach
    void setup() {
        comp1 = new Competency(competency1);
        comp2 = new Competency(competency2);

        exp1 = new Expertise("exp1");
        exp2 = new Expertise("exp2");

        post = new Post();
        postReturned = new Post();

        post.setId(1L);
        postReturned.setId(1L);

        post.setAuthor("despacito");
        postReturned.setAuthor("despacito");

        post.setPricePerHour(new BigDecimal("15.00"));
        postReturned.setPricePerHour(new BigDecimal("15.00"));

        post.getCompetencySet().add(comp1);
        postReturned.getCompetencySet().add(comp1);

        post.getExpertiseSet().add(exp1);
        postReturned.getExpertiseSet().add(exp1);

        Set<Post> tmpSet = new HashSet<>();
        tmpSet.add(post);
        comp1.setPostSet(tmpSet);

        post1 = new Post();
        post1Returned = new Post();

        post1.setId(2L);
        post1Returned.setId(2L);

        post1.setAuthor("lillolallo");
        post1Returned.setAuthor("lillolallo");

        post1.setPricePerHour(new BigDecimal("100.00"));
        post1Returned.setPricePerHour(new BigDecimal("100.00"));

        post1.getCompetencySet().add(comp2);
        post1Returned.getCompetencySet().add(comp2);

        post1.getExpertiseSet().add(exp1);
        post1Returned.getExpertiseSet().add(exp1);

        tmpSet = new HashSet<>();
        tmpSet.add(post1);
        comp2.setPostSet(tmpSet);
        tmpSet = new HashSet<>();
        tmpSet.add(post);
        tmpSet.add(post1);
        exp1.setPostSet(tmpSet);

        Mockito.when(postRepository.getPostById(1L)).thenReturn(postReturned);
        Mockito.when(postRepository.getPostById(2L)).thenReturn(post1Returned);
        Mockito.when(postRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Mockito.when(postRepository.save(post)).thenReturn(postReturned);
        Mockito.when(postRepository.save(post1)).thenReturn(post1Returned);
        Mockito.when(expertiseRepository.existsById(any())).thenReturn(false);
        Mockito.when(expertiseRepository.existsById("exp1")).thenReturn(false, true);
        Mockito.when(competencyRepository.existsById(any())).thenReturn(false);
        Mockito.when(competencyRepository.existsById(competency1)).thenReturn(false, true);
        Mockito.when(competencyRepository.existsById(competency2)).thenReturn(false, true);
        Mockito.when(postRepository.existsById(any())).thenReturn(false);
        Mockito.when(postRepository.existsById(1L)).thenReturn(true);
        Mockito.when(postRepository.existsById(2L)).thenReturn(true);

        Mockito.when(postRepository.findAll()).thenReturn(List.of(postReturned, post1Returned));

        Mockito.when(competencyRepository.getAllBySearchableStringContaining(competency1))
            .thenReturn(List.of(comp1));

        Mockito.when(competencyRepository.getAllBySearchableStringContaining(competency2))
            .thenReturn(List.of(comp2));

        Mockito.when(expertiseRepository.getAllBySearchableStringContaining(expertise1))
            .thenReturn(List.of(exp1));

        Mockito.when(expertiseRepository.getAllBySearchableStringContaining(""))
            .thenReturn(List.of(exp1, exp2));

        Mockito.when(postRepository.getAllByCompetencySetContaining(comp1))
            .thenReturn(List.of(postReturned));
        Mockito.when(postRepository.getAllByCompetencySetContaining(comp2))
            .thenReturn(List.of(post1Returned));

        Mockito.when(postRepository.getAllByExpertiseSetContaining(exp1))
            .thenReturn(List.of(postReturned, post1Returned));

        Mockito.when(competencyRepository.getCompetencyByCompetencyString(competency1))
            .thenReturn(comp1);
        Mockito.when(competencyRepository.getCompetencyByCompetencyString(competency2))
            .thenReturn(comp2);

        Mockito.when(expertiseRepository.getExpertiseByExpertiseString(expertise1))
            .thenReturn(exp1);


    }

    @Test
    void createPost() {
        Post tmp = postService.createPost(post);
        assertThat(tmp).isEqualTo(postReturned);

        tmp.setAuthor("anotherguy");
        tmp = postService.createPost(tmp);

        assertThat(tmp.getAuthor()).isEqualTo("anotherguy");
        Post tmp2 = postService.createPost(post1);
        assertThat(tmp2).isEqualTo(post1);
    }


    @Test
    void editPost() {

        Post tmp = postService.createPost(post);

        tmp.setPricePerHour(new BigDecimal("42.00"));
        tmp.getCompetencySet().add(comp2);

        Post edited = postService.editPost(tmp, "1");

        Post retrieved = postRepository.getPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);

        assertThat(edited.getPricePerHour()).isEqualTo(new BigDecimal("42.00"));

        assertThat(edited.getCompetencySet()).hasSize(2).containsOnlyOnce(comp2);

    }

    @Test
    void editPostNaN() {
        assertThatThrownBy(() -> postService.editPost(null, "agagaga"));
    }

    @Test
    void editNonExistentPost() {
        Post toEdit = new Post();
        //toEdit.setId(realId + 100L); // set id to something different from existing post

        assertThatThrownBy(() -> postService.editPost(toEdit, "999999")).isInstanceOf(
            PostNotFoundException.class);
        postRepository.delete(toEdit);

    }

    @Test
    void editPostFailingNetId() {
        Post tmp = postService.createPost(post);

        Post toEdit = new Post();
        toEdit.setId(tmp.getId());
        Competency newCompetency = comp2;

        toEdit.setPricePerHour(new BigDecimal("42.00"));
        toEdit.getCompetencySet().add(newCompetency);

        toEdit.setAuthor("anotherguy");


        assertThatThrownBy(() -> postService.editPost(toEdit, "1")).isInstanceOf(
            InvalidEditException.class);

    }

    @Test
    void testSearchByKeywordFoundComp() {
        Post tmp = postService.createPost(post);
        Collection<Post> result = postService.searchByKeyword("cOmp  1");
        assertThat(result).containsOnlyOnce(tmp).hasSize(1);
    }

    @Test
    void testSearchByKeywordFoundExp() {
        Post tmp = postService.createPost(post);
        Collection<Post> result = postService.searchByKeyword("EXp  1");
        assertThat(result).containsOnlyOnce(tmp).hasSize(2);
    }

    @Test
    void testSearchByKeywordNotFound() {
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