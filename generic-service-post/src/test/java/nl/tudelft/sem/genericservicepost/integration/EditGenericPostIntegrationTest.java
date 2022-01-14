package nl.tudelft.sem.genericservicepost.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Collection;
import java.util.Set;
import javax.ws.rs.core.MediaType;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.genericservicepost.repositories.GenericPostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EditGenericPostIntegrationTest {

    private final transient String baseUrl = "/genericpost";

    private final transient Expertise expertise1 = new Expertise("exp1");
    private final transient Expertise expertise2 = new Expertise("exp2");
    private final transient FilterProvider filterClientSide =
            new SimpleFilterProvider().addFilter("postFilter",
                    SimpleBeanPropertyFilter.serializeAllExcept("user"));
    private transient GenericPost genericPost;
    private transient GenericPost genericPost2;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient GenericPostRepository genericPostRepository;

    @Autowired
    private transient ExpertiseRepository expertiseRepository;

    @BeforeEach
    void setup() {
        genericPost = new GenericPost();
        genericPost.setAuthor("author1");
        genericPost.setHoursPerWeek(10);
        genericPost.setDuration(3);
        genericPost.setExpertiseSet(Set.of(expertise1));

        genericPost2 = new GenericPost();
        genericPost2.setAuthor("author2");
        genericPost2.setHoursPerWeek(15);
        genericPost2.setDuration(4);
        genericPost2.setExpertiseSet(Set.of(expertise1, expertise2));
    }

    @AfterEach
    void teardown() {
        expertiseRepository.deleteAllInBatch();
        genericPostRepository.deleteAllInBatch();
    }

    private GenericPost addGenericPost(GenericPost post) {
        String url = baseUrl + "/create";
        try {
            String serializedPost =
                    new ObjectMapper().writer(filterClientSide).writeValueAsString(post);

            String body = mockMvc.perform(
                            post(url).content(serializedPost).contentType(MediaType.APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isCreated()).andReturn().getResponse()
                    .getContentAsString();
            return new ObjectMapper().readValue(body, GenericPost.class);
        } catch (Exception e) {
            return null;
        }
    }

    private GenericPost editGenericPost(GenericPost post) {
        String url = "/genericpost" + "/edit?genericPostId=" + post.getId();
        try {
            String serializedPost =
                    new ObjectMapper().writer(filterClientSide).writeValueAsString(post);
            String body = this.mockMvc.perform(patch(url).content(serializedPost)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isAccepted())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, GenericPost.class);

        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void editPostTest() {
        GenericPost expected = addGenericPost(genericPost);
        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();
        int newHours = 6;

        expected.setHoursPerWeek(newHours);
        GenericPost edited = editGenericPost(expected);
        assertThat(edited).isNotNull();
        assertThat(edited.getHoursPerWeek()).isEqualTo(newHours);
        assertThat(genericPostRepository.getGenericPostById(edited.getId())).isEqualTo(edited);
    }
}
