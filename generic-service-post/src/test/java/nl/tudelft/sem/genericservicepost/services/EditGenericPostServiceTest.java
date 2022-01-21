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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EditGenericPostServiceTest {

    transient GenericPost genericPost;

    @Mock
    transient GenericPostRepository postRepository;

    @InjectMocks
    transient EditGenericPostService moreService;

    @Autowired
    transient GenericPostService genericPostService;

    @BeforeEach
    void setup() {
        genericPost = new GenericPost();

        genericPost.setId(0L);

        genericPost.setAuthor("The Rock");

        genericPost.setHoursPerWeek(15);

        genericPost.setDuration(5);

        genericPost.getExpertiseSet().add(new Expertise("ABC"));
    }

    @Test
    void editGenericPostTest() {

        GenericPost tmp = genericPostService.createGenericPost(genericPost);
        Mockito.when(postRepository.findById(tmp.getId())).thenReturn(java.util.Optional.of(tmp));
        Mockito.when(postRepository.getGenericPostById(tmp.getId())).thenReturn(tmp);
        Mockito.when(postRepository.save(tmp)).thenReturn(tmp);
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

        Mockito.when(postRepository.findById(toEdit.getId()))
                .thenReturn(java.util.Optional.of(tmp));
        Mockito.when(postRepository.getGenericPostById(toEdit.getId())).thenReturn(tmp);

        assertThatThrownBy(() -> moreService
                .editGenericPost(toEdit, toEdit.getId().toString())).isInstanceOf(
                InvalidEditException.class);

    }

}