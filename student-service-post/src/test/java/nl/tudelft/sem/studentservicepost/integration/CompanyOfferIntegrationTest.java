package nl.tudelft.sem.studentservicepost.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.repositories.ChangedOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import nl.tudelft.sem.studentservicepost.services.PostService;
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
public class CompanyOfferIntegrationTest {

    private final transient String baseUrl = "/offers";
    private final transient Competency competency1 = new Competency("comp1");
    private final transient Competency competency2 = new Competency("comp2");
    private final transient Requirement requirement1 = new Requirement("emacs");
    private final transient Requirement requirement2 = new Requirement("vim");
    private final transient Expertise expertise1 = new Expertise("exp1");
    private final transient Expertise expertise2 = new Expertise("exp2");
    private final transient CompanyOffer companyOffer = new CompanyOffer();
    private final transient CompanyOffer companyOffer1 = new CompanyOffer();

    private final transient Post post = new Post();
    private final transient Post post1 = new Post();

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient CompanyOfferRepository companyOfferRepository;

    @Autowired
    private transient PostRepository postRepository;

    @Autowired
    private transient PostService postService;

    @Autowired
    private transient CompetencyRepository competencyRepository;

    @Autowired
    private transient ExpertiseRepository expertiseRepository;

    @Autowired
    private transient RequirementRepository requirementRepository;

    @Autowired
    private transient ChangedOfferRepository changedOfferRepository;

    @BeforeEach
    void setup() {
        companyOffer.setId(1L);
        companyOffer.setCompanyId("big money srl");
        companyOffer.setWeeklyHours(10);
        companyOffer.setTotalHours(80);
        companyOffer.setExpertise(Set.of(expertise1));
        companyOffer.setPricePerHour(new BigDecimal("1.00"));
        companyOffer.setRequirementsSet(Set.of(requirement1));

        companyOffer1.setId(2L);
        companyOffer1.setCompanyId("small money spa");
        companyOffer1.setWeeklyHours(5);
        companyOffer1.setTotalHours(2000);
        companyOffer1.setExpertise(Set.of(expertise2));
        companyOffer1.setPricePerHour(new BigDecimal("3.00"));
        companyOffer1.setRequirementsSet(Set.of(requirement2));

        ChangedOffer changedOffer = new ChangedOffer(companyOffer);

        changedOffer.setPricePerHour(new BigDecimal("100.00"));

        // add a post with ID 1
        post.setId(1L);
        post.setAuthor("author1");
        post.setPricePerHour(new BigDecimal("12.00"));
        post.getCompetencySet().add(competency1);
        post.getCompetencySet().add(competency2);
        post.getExpertiseSet().add(expertise1);

        // add a post with ID 2
        post1.setId(2L);
        post1.setAuthor("author2");
        post1.setPricePerHour(new BigDecimal("28.00"));
        post1.getCompetencySet().add(competency2);
        post1.getExpertiseSet().add(expertise1);
        post1.getExpertiseSet().add(expertise2);

        postService.createPost(post);
        postService.createPost(post1);

    }

    @AfterEach
    void teardown() {
        requirementRepository.deleteAllInBatch();
        competencyRepository.deleteAllInBatch();
        expertiseRepository.deleteAllInBatch();
        changedOfferRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        companyOfferRepository.deleteAllInBatch();
    }


    private CompanyOffer createOffer(CompanyOffer toAdd, long id) {
        String url = baseUrl + "/create?postId=" + id;
        String serializedOffer;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            serializedOffer = objectMapper.writeValueAsString(toAdd);
            String body = this.mockMvc.perform(
                    post(url).content(serializedOffer).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();
            return objectMapper.readValue(body, CompanyOffer.class);
        } catch (Exception e) {
            return null;
        }
    }


    private Collection<CompanyOffer> retrieveOffers(long id) {
        String url = baseUrl + "/getByPostId?postId=" + id;
        try {
            String body =
                this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body,
                new TypeReference<>() {
                });

        } catch (Exception e) {
            return null;
        }
    }

    private ChangedOffer suggestChange(CompanyOffer toChange) {
        ObjectMapper objectMapper = new ObjectMapper();
        String url = baseUrl + "/suggestChange";
        try {
            String serializedChangedOffer = objectMapper.writeValueAsString(toChange);
            String body = this.mockMvc.perform(
                    patch(url).content(serializedChangedOffer)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
            return objectMapper.readValue(body, ChangedOffer.class);
        } catch (Exception e) {
            return null;
        }
    }


    private Collection<ChangedOffer> getChanges(long id) {
        String url = baseUrl + "/getChanges?offerId=" + id;
        try {
            String body =
                this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body,
                new TypeReference<>() {
                });
        } catch (Exception e) {
            return null;
        }
    }

    private CompanyOffer acceptChange(long id) {
        String url = baseUrl + "/acceptChanges?changedId=" + id;
        try {
            String body =
                this.mockMvc.perform(patch(url)).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, CompanyOffer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CompanyOffer acceptOffer(long changedId) {
        String url = baseUrl + "/acceptOffer?offerId=" + changedId;
        try {
            String body =
                this.mockMvc.perform(patch(url)).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return new ObjectMapper().readValue(body, CompanyOffer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void createOfferTest() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();

        assertThat(expected).isEqualTo(companyOffer);
    }

    @Test
    void getByPostId() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());
        createOffer(companyOffer1, post1.getId()); // add a second one

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isNotNull();

        Collection<CompanyOffer> retrieved = retrieveOffers(post.getId());

        assertThat(retrieved).isNotNull();

        assertThat(retrieved).containsExactly(companyOffer);
    }

    @Test
    void suggestChangeTest() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());
        assertThat(expected).isNotNull();
        BigDecimal newPrice = new BigDecimal("2000.00");
        expected.setPricePerHour(newPrice);
        ChangedOffer changed = suggestChange(expected);

        assertThat(changed).isNotNull();
        assertThat(changed.getPricePerHour()).isEqualTo(newPrice);
        assertThat(changedOfferRepository.existsById(changed.getId())).isTrue();
    }

    @Test
    void getChangesTest() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());
        assertThat(expected).isNotNull();
        BigDecimal newPrice = new BigDecimal("2000.00");
        expected.setPricePerHour(newPrice);
        ChangedOffer changed = suggestChange(expected);

        Collection<ChangedOffer> received = getChanges(expected.getId());

        assertThat(received).containsExactly(changed);
    }

    @Test
    void acceptChangeTest() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());
        assertThat(expected).isNotNull();
        BigDecimal newPrice = new BigDecimal("6969.00");
        expected.setPricePerHour(newPrice);
        ChangedOffer changed = suggestChange(expected);

        assertThat(changed).isNotNull();
        CompanyOffer received = acceptChange(changed.getId());

        assertThat(received).isNotNull();
        CompanyOffer retrieved = companyOfferRepository.getById(expected.getId());
        assertThat(retrieved.getPricePerHour()).isEqualTo(newPrice);
        assertThat(received).isEqualTo(expected); // check equals method to see why
    }

    @Test
    void acceptOfferTest() {
        CompanyOffer expected = createOffer(companyOffer, post.getId());

        assertThat(expected).isNotNull();

        expected = acceptOffer(expected.getId());

        assertThat(expected).isNotNull();
        CompanyOffer retrieved = companyOfferRepository.getById(expected.getId());
        assertThat(retrieved.isAccepted()).isTrue();

    }

}
