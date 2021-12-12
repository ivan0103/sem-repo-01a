package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyOfferServiceTest {

    transient Post post;

    @InjectMocks
    transient CompanyOfferService companyOfferService;

    @Mock
    transient CompanyOfferRepository companyOfferRepository;

    @Mock
    transient PostService postService;

    @Mock
    transient PostRepository postRepository;

    @Mock
    transient RequirementRepository requirementRepository;

    @Mock
    transient ExpertiseRepository expertiseRepository;

    final transient String reqString = "being smort";

    final transient String expString = "being not stupid";

    final transient String companyName = "あなたばか";


    @BeforeEach
    void setup() {

        when(postRepository.existsById(any())).thenReturn(false);
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postService.createPost(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(companyOfferRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());


        post = new Post();
        post.setId(0L);
        post.setAuthor("despacito");
        post.setPricePerHour(new BigDecimal("15.0"));
        post.getCompetencySet().add(new Competency("comp1"));
        post.getExpertiseSet().add(new Expertise("exp1"));

        post = postService.createPost(post);

        post.setId(1L);

        when(postRepository.getPostById(1L)).thenReturn(post);

        when(requirementRepository.existsById(any())).thenReturn(false);
        when(requirementRepository.existsById(reqString)).thenReturn(true);
        when(requirementRepository.getRequirementByRequirementString(reqString)).thenReturn(
            new Requirement(reqString));

        when(expertiseRepository.existsById(any())).thenReturn(false);
        when(expertiseRepository.existsById(expString)).thenReturn(true);
        when(expertiseRepository.getExpertiseByExpertiseString(expString)).thenReturn(
            new Expertise(expString));

        when(companyOfferRepository.getAllByPost(any())).thenReturn(new HashSet<>());


    }

    @Test
    void createOffer() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        assertThat(inserted).isEqualTo(companyOffer);
    }

    @Test
    void createOfferNoPost() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId("scemo chi legge");
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        assertThatThrownBy(() -> companyOfferService.createOffer(companyOffer, "12"))
            .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void createOfferInvalidId() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId("scemo chi legge");
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);


        assertThatThrownBy(() -> companyOfferService.createOffer(companyOffer, "a valid id lol"))
            .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void getByPostId() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        when(companyOfferRepository.getAllByPost(post)).thenReturn(
            new HashSet<>(List.of(inserted)));

        Set<CompanyOffer> result = companyOfferService.getByPostId(postId);

        assertThat(result).hasSize(1).containsOnlyOnce(inserted);

    }

    @Test
    void getByPostIdEmpty() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        when(companyOfferRepository.getAllByPost(post)).thenReturn(
            new HashSet<>(List.of(inserted)));

        assertThatThrownBy(() -> companyOfferService.getByPostId("12")).isInstanceOf(
            PostNotFoundException.class);


    }

    @Test
    void getByPostIdWrong() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        when(companyOfferRepository.getAllByPost(post)).thenReturn(
            new HashSet<>(List.of(inserted)));

        assertThatThrownBy(() -> companyOfferService.getByPostId("lmao")).isInstanceOf(
           PostNotFoundException.class);

    }

}