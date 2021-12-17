package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.StudentOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentOfferServiceTest {
    transient GenericPost genericPost;

    @InjectMocks
    transient StudentOfferService studentOfferService;

    @Mock
    transient GenericPostRepository genericPostRepository;

    @Mock
    transient GenericPostService genericPostService;

    @Mock
    transient StudentOfferRepository studentOfferRepository;

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
        genericPost.getExpertiseSet().add(new Expertise("2B"));

        when(genericPostRepository.getGenericPostById((long) 1)).thenReturn(genericPost);

    }

    @Test
    void createStudentOfferTest(){
        StudentOffer studentOffer = new StudentOffer();
        studentOffer.setStudentId("Automata");
        String postId = genericPost.getId().toString();
        StudentOffer inserted = studentOfferService.createStudentOffer(studentOffer, postId);

        assertThat(inserted).isEqualTo(studentOffer);
    }

    @Test
    void getByGenericPostIdTest(){
        StudentOffer studentOffer = new StudentOffer();
        studentOffer.setStudentId("Replicant");
        String postId = genericPost.getId().toString();
        StudentOffer inserted = studentOfferService.createStudentOffer(studentOffer, postId);

        Set<StudentOffer> set = new HashSet<>();
        set.add(inserted);
        when(studentOfferRepository.getAllByGenericPost(genericPost)).thenReturn(set);

        Set<StudentOffer> result = studentOfferService.getByGenericPostId(postId);

        assertThat(result).hasSize(1).containsOnlyOnce(inserted);
    }
}
