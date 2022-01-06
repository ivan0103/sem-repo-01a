package nl.tudelft.sem.studentservicepost.controllers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.User;
import nl.tudelft.sem.studentservicepost.entities.UserImpl;
import nl.tudelft.sem.studentservicepost.entities.UserImplProxy;
import nl.tudelft.sem.studentservicepost.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class PostControllerTest {

    private final transient String baseUrl = "/servicepost";

    private final transient FilterProvider filters =
        new SimpleFilterProvider().addFilter("postFilter",
            SimpleBeanPropertyFilter.serializeAllExcept("user"));

    private final transient Post post = new Post();
    private transient String serializedPost;
    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient PostService postService;

    @BeforeEach
    void setup() {
        post.setAuthor("abcd");
        post.setCompetencySet(Set.of(new Competency("being good")));
        post.setPricePerHour(new BigDecimal("12.00"));
        post.setExpertiseSet(Set.of(new Expertise("computers")));
        try {
            serializedPost = new ObjectMapper().writer(filters).writeValueAsString(post);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
        when(postService.createPost(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(postService.editPost(any(), any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        when(postService.searchByKeyword(any())).thenReturn(List.of());
        when(postService.searchByKeyword("computer")).thenReturn(List.of(post));
    }

    @Test
    void createPost() {
        String url = baseUrl + "/create";
        try {
            this.mockMvc.perform(post(url).content(serializedPost)
                    .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in creating post");
        }
    }

    @Test
    void editPost() {
        String url = baseUrl + "/edit?postId=1";
        try {
            this.mockMvc.perform(patch(url).content(serializedPost)
                    .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isAccepted());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in editing post");
        }
    }

    @Test
    void searchPostsByKeywords() {
        String url = baseUrl + "/search";
        String keywords = "[\"linux\", \"computer\", \"js\"]";
        try {
            this.mockMvc.perform(get(url).content(keywords).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isFound());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in getting posts by keywords");
        }
    }

    @Test
    void testGetAll() {
        String url = baseUrl + "/getall";
        try {
            this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isFound());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in getting posts");
        }
    }
}