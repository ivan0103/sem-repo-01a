package nl.tudelft.sem.genericservicepost.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.StudentOffer;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import nl.tudelft.sem.genericservicepost.repositories.StudentOfferRepository;
import nl.tudelft.sem.genericservicepost.services.GenericPostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentOfferIntegrationTest {

    private final transient String baseUrl = "/studentoffers";
    private final transient StudentOffer studentOffer = new StudentOffer();
    private final transient StudentOffer studentOffer2 = new StudentOffer();
    private final transient Expertise expertise1 = new Expertise("exp1");
    private final transient Expertise expertise2 = new Expertise("exp2");

    private final transient GenericPost genericPost = new GenericPost();
    private final transient GenericPost genericPost2 = new GenericPost();

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient GenericPostRepository genericPostRepository;

    @Autowired
    private transient ExpertiseRepository expertiseRepository;

    @Autowired
    private transient StudentOfferRepository studentOfferRepository;

    @Autowired
    private transient GenericPostService genericPostService;

    @BeforeEach
    void setup() {
        studentOffer.setId(1L);
        studentOffer.setStudentId("firstId");

        studentOffer2.setId(2L);
        studentOffer2.setStudentId("secondId");

        genericPost.setId(1L);
        genericPost.setAuthor("author1");
        genericPost.setHoursPerWeek(10);
        genericPost.setDuration(3);
        genericPost.setExpertiseSet(Set.of(expertise1));

        genericPost2.setId(2L);
        genericPost2.setAuthor("author2");
        genericPost2.setHoursPerWeek(15);
        genericPost2.setDuration(4);
        genericPost2.setExpertiseSet(Set.of(expertise1, expertise2));

        genericPostService.createGenericPost(genericPost);
        genericPostService.createGenericPost(genericPost2);
    }

    @AfterEach
    void teardown() {
        expertiseRepository.deleteAllInBatch();
        genericPostRepository.deleteAllInBatch();
        studentOfferRepository.deleteAllInBatch();
    }

    private StudentOffer createOffer(StudentOffer toAdd, long id) {
        String url = baseUrl + "/create?genericPostId=" + id;
        try {
            String serializedOffer = new ObjectMapper().writeValueAsString(toAdd);
            String body = this.mockMvc.perform(post(url).content(serializedOffer)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, StudentOffer.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Collection<StudentOffer> getOffers(long id) {
        String url = baseUrl + "/getByGenericPostId?genericPostId=" + id;
        try {
            String body = this.mockMvc
                    .perform(get(url))
                    .andDo(print()).andExpect(status().isFound())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void createOfferTest() {
        StudentOffer expected = createOffer(studentOffer, genericPost.getId());
        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();
        assertThat(expected).isEqualTo(studentOffer);
    }

    @Test
    void getByGenericPostIdTest() {
        StudentOffer expected = createOffer(studentOffer, genericPost.getId());
        createOffer(studentOffer2, genericPost2.getId());

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();

        Collection<StudentOffer> retrieved = getOffers(genericPost.getId());

        assertThat(retrieved).isNotNull();
        assertThat(retrieved).containsExactly(studentOffer);
    }
}
