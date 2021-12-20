package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.exceptions.GenericPostNotFoundException;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.StudentOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentOfferServiceTest {
    transient GenericPost genericPost;
    transient StudentOffer studentOffer;
    transient StudentOffer applied;
    transient String postId;

    @InjectMocks
    transient StudentOfferService studentOfferService;

    @Mock
    transient GenericPostRepository genericPostRepository;

    @Mock
    transient GenericPostService genericPostService;

    @Mock
    transient StudentOfferRepository studentOfferRepository;

    @Mock
    transient ExpertiseRepository expertiseRepository;

    @BeforeEach
    void setup(){
        when(genericPostRepository.existsById(any())).thenReturn(false);
        when(genericPostRepository.existsById(1L)).thenReturn(true);
        when(genericPostService.createGenericPost(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(studentOfferRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        genericPost = new GenericPost();
        genericPost.setId((long) 1);
        genericPost.setAuthor("Nier");
        genericPost.setHoursPerWeek(15);
        genericPost.setDuration(5);
        genericPost.getExpertiseSet().add(new Expertise("Android"));

        when(genericPostRepository.getGenericPostById((long) 1)).thenReturn(genericPost);
        when(studentOfferRepository.getAllByGenericPost(any())).thenReturn(new HashSet<>());

        studentOffer = new StudentOffer();
        studentOffer.setStudentId("Automata");
        postId = genericPost.getId().toString();
        applied = studentOfferService.createStudentOffer(studentOffer, postId);
    }

    @Test
    void createStudentOfferTest(){
        assertThat(applied).isEqualTo(studentOffer);
    }

    @Test
    void getByGenericPostIdTest(){
        when(studentOfferRepository.getAllByGenericPost(genericPost)).thenReturn(new HashSet<>(List.of(applied)));

        Set<StudentOffer> result = studentOfferService.getByGenericPostId(postId);

        assertThat(result).hasSize(1).containsOnlyOnce(applied);
    }

    @Test
    void getByGenericPostInvalidIdTest(){
        when(studentOfferRepository.getAllByGenericPost(genericPost)).thenReturn(new HashSet<>(List.of(applied)));
        assertThatThrownBy(() -> studentOfferService.getByGenericPostId(postId + 1)).isInstanceOf(
                GenericPostNotFoundException.class);
    }
}
