package nl.tudelft.sem.genericservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EditGenericPostServiceTest {
    transient GenericPost genericPost;
    transient GenericPost genericPost1;

    @Autowired
    transient GenericPostRepository postRepository;

    @Autowired
    transient EditGenericPostService moreService;

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
    void editGenericPostTest() {
        GenericPost tmp = genericPostService.createGenericPost(genericPost);

        tmp.setDuration(3);

        GenericPost edited = moreService.editGenericPost(tmp, tmp.getId().toString());

        GenericPost retrieved = postRepository.getGenericPostById(tmp.getId());

        assertThat(edited).isEqualTo(retrieved);
        assertThat(edited.getDuration()).isEqualTo(3);
    }

    @Test
    void editGenericPostNaN() {
        assertThatThrownBy(() -> moreService.editGenericPost(null, "AH"));
    }

    @Test
    void editNonExistentPost() {
        GenericPost toEdit = new GenericPost();
        //toEdit.setId(realId + 100L); // set id to something different from existing post

        assertThatThrownBy(() -> moreService.editGenericPost(toEdit, "999999")).isInstanceOf(
                GenericPostNotFoundException.class);
        postRepository.delete(toEdit);

    }

    @Test
    void editPostFailingNetId() {
        GenericPost tmp = genericPostService.createGenericPost(genericPost);

        GenericPost toEdit = new GenericPost();
        toEdit.setId(tmp.getId());

        toEdit.setAuthor("Jeff");


        assertThatThrownBy(() -> moreService.editGenericPost(toEdit, "1")).isInstanceOf(
                GenericPostNotFoundException.class);

    }
}