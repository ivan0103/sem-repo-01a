package nl.tudelft.sem.studentservicepost.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.UserImpl;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
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
public class PostIntegrationTest {

    private final transient String baseUrl = "/servicepost";

    private final transient Competency competency1 = new Competency("comp1");
    private final transient Competency competency2 = new Competency("comp2");

    private final transient Expertise expertise1 = new Expertise("exp1");
    private final transient Expertise expertise2 = new Expertise("exp2");
    private final transient FilterProvider filterClientSide =
        new SimpleFilterProvider().addFilter("postFilter",
            SimpleBeanPropertyFilter.serializeAllExcept("user"));
    private transient Post post;
    private transient Post post2;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient PostRepository postRepository;

    @Autowired
    private transient ExpertiseRepository expertiseRepository;

    @Autowired
    private transient CompetencyRepository competencyRepository;


    @BeforeEach
    void setup() {
        post = new Post();
        post.setAuthor("author1");
        post.setPricePerHour(new BigDecimal("12.00"));
        post.setCompetencySet(Set.of(competency1, competency2));
        post.setExpertiseSet(Set.of(expertise1));

        post2 = new Post();
        post2.setAuthor("author2");
        post2.setPricePerHour(new BigDecimal("22.00"));
        post2.setCompetencySet(Set.of(competency1, competency2));
        post2.setExpertiseSet(Set.of(expertise1, expertise2));
    }

    @AfterEach
    void teardown() {
        expertiseRepository.deleteAllInBatch();
        competencyRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
    }


    private Post addPost(Post post) {
        String url = baseUrl + "/create";
        Post result;
        try {
            String serializedPost =
                new ObjectMapper().writer(filterClientSide).writeValueAsString(post);

            String body = mockMvc.perform(
                    post(url).content(serializedPost).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();
            return new ObjectMapper().readValue(body, Post.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Post editPost(Post post) {
        String url = baseUrl + "/edit?postId=" + post.getId();
        try {
            String serializedPost =
                new ObjectMapper().writer(filterClientSide).writeValueAsString(post);

            String body = this.mockMvc.perform(
                    patch(url).content(serializedPost).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isAccepted()).andReturn().getResponse()
                .getContentAsString();
            return new ObjectMapper().readValue(body, Post.class);

        } catch (Exception e) {
            return null;
        }
    }

    private Post getPost(long id, String author) throws JsonProcessingException {
        UserImpl user = new UserImpl();
        user.setNetID(author);
        user.setName("Name");
        /*
            Mocked user microservice to test integration and interactions.
         */
        ObjectMapper objectMapper = new ObjectMapper();
        WireMockServer server = new WireMockServer(8080);
        server.start();
        configureFor("localhost", 8080);
        String serializedUser = objectMapper.writeValueAsString(user);
        stubFor(WireMock.get("/users/" + author).willReturn(
            ok().withBody(serializedUser).withHeader("Content-Type", "application/json")));

        try {
            String url = baseUrl + "/retrieve/" + id;
            String body =
                this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isFound())
                    .andReturn().getResponse().getContentAsString();
            return objectMapper.readValue(body, Post.class);
        } catch (Exception e) {
            return null;
        } finally {
            server.stop();
        }
    }

    private Collection<Post> searchByK(String keyword) {
        String url = baseUrl + "/search";
        String keywords = "[\"" + keyword + "\"]";
        try {
            String body = this.mockMvc.perform(
                    get(url).content(keywords).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isFound()).andReturn().getResponse()
                .getContentAsString();
            return new ObjectMapper().readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            return null;
        }
    }


    private Collection<Post> getAll() {
        String url = baseUrl + "/getall";
        try {
            String body =
                this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isFound())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void createPost() {

        Post expected = addPost(post);
        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();
        assertThat(postRepository.existsById(expected.getId())).isTrue();
    }

    @Test
    void editPostTest() {
        Post expected = addPost(post);
        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();
        BigDecimal newPrice = new BigDecimal("20.00");

        expected.setPricePerHour(newPrice);

        Post edited = editPost(expected);
        assertThat(edited).isNotNull();

        assertThat(edited.getPricePerHour()).isEqualTo(newPrice);

        assertThat(postRepository.getPostById(edited.getId())).isEqualTo(edited);

    }

    @Test
    void getById() throws JsonProcessingException {
        Post expected = addPost(post);

        assertThat(expected).isNotNull();

        long id = expected.getId();

        Post returned = getPost(id, "author1");

        assertThat(returned).isNotNull();

        assertThat(returned).isEqualTo(expected);

    }

    @Test
    void searchTest() {
        addPost(post);
        Post p2 = addPost(post2);

        Collection<Post> returned = searchByK("exp2");

        assertThat(returned).isNotNull();

        assertThat(returned).containsExactly(p2);

    }

    @Test
    void getAllTest() {
        Post p1 = addPost(post);
        Post p2 = addPost(post2);

        Collection<Post> returned = getAll();

        assertThat(returned).isNotNull();

        assertThat(returned).containsExactly(p1, p2);

    }


}
