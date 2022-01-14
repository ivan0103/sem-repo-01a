package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Contract;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.OfferNotAcceptedException;
import nl.tudelft.sem.studentservicepost.exceptions.OfferNotFoundException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.ChangedOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CompanyOfferServiceTest {

    final transient String reqString = "being smort";
    final transient String expString = "being not stupid";
    final transient String companyName = "あなたばか";
    final transient String price = "12.00";

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
    @Mock
    transient ChangedOfferRepository changedOfferRepository;
    @Mock
    transient RestTemplate restTemplate;
    @Mock
    transient ResponseEntity<Contract> responseEntity;

    @BeforeEach
    void setup() {

        when(postRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postService.createPost(ArgumentMatchers.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(companyOfferRepository.save(ArgumentMatchers.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());


        post = new Post();
        post.setId(0L);
        post.setAuthor("despacito");
        post.setPricePerHour(new BigDecimal("15.0"));
        post.getCompetencySet().add(new Competency("comp1"));
        post.getExpertiseSet().add(new Expertise("exp1"));

        post = postService.createPost(post);

        post.setId(1L);

        when(postService.getById("1")).thenReturn(post);

        when(requirementRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(requirementRepository.existsById(reqString)).thenReturn(false, true);
        when(requirementRepository.getRequirementByRequirementString(reqString)).thenReturn(
            new Requirement(reqString));

        when(expertiseRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(expertiseRepository.existsById(expString)).thenReturn(false, true);
        when(expertiseRepository.getExpertiseByExpertiseString(expString)).thenReturn(
            new Expertise(expString));

        when(companyOfferRepository.getAllByPost(ArgumentMatchers.any()))
                .thenReturn(new HashSet<>());

        when(companyOfferRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(companyOfferRepository.existsById(1L)).thenReturn(true);

        when(changedOfferRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(changedOfferRepository.existsById(2L)).thenReturn(true);


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

        //insert a very similar second one
        inserted.setCompanyId("another company");
        inserted = companyOfferService.createOffer(companyOffer, postId);
        assertThat(inserted.getCompanyId()).isEqualTo("another company");

    }

    @Test
    void createOfferNoPost() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId("scemo chi legge");
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        when(postService.getById("12")).thenThrow(PostNotFoundException.class);

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

        when(postService.getById("a valid id lol")).thenThrow(PostNotFoundException.class);

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

        when(postService.getById("12")).thenThrow(PostNotFoundException.class);

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

        when(postService.getById("lmao")).thenThrow(PostNotFoundException.class);

        assertThatThrownBy(() -> companyOfferService.getByPostId("lmao")).isInstanceOf(
                PostNotFoundException.class);

    }


    @Test
    void editOffer() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        inserted.setPricePerHour(new BigDecimal("101.00"));

        inserted.setId(1L);

        ChangedOffer changed = companyOfferService.suggestChange(inserted);

        inserted.setId(null);

        assertThat(changed.getPricePerHour().floatValue()).isCloseTo(101.0f,
            Percentage.withPercentage(1));
    }

    @Test
    void editOfferNotFound() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        inserted.setPricePerHour(new BigDecimal("100.00"));

        inserted.setId(100L);

        assertThatThrownBy(() -> companyOfferService.suggestChange(inserted)).isInstanceOf(
            OfferNotFoundException.class);
    }

    @Test
    void getChanges() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        inserted.setPricePerHour(new BigDecimal("100.00"));

        inserted.setId(1L);

        ChangedOffer change = companyOfferService.suggestChange(inserted);

        inserted.getChangedOffers().add(change); //db action
        change.setParent(inserted);
        when(companyOfferRepository.getById(1L)).thenReturn(inserted);

        Set<ChangedOffer> result = companyOfferService.getChanges("1");

        assertThat(result).hasSize(1).containsOnlyOnce(change);

        assertThatThrownBy(() -> companyOfferService.getChanges("liao")).isInstanceOf(
            OfferNotFoundException.class);
    }

    @Test
    void getChangesNoOffer() {
        assertThatThrownBy(() -> companyOfferService.getChanges("12345")).isInstanceOf(
            OfferNotFoundException.class);
    }

    @Test
    void acceptChange() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        inserted.setPricePerHour(new BigDecimal("100.00"));

        inserted.setId(1L);

        ChangedOffer change = companyOfferService.suggestChange(inserted);

        inserted.getChangedOffers().add(change); //db action
        change.setParent(inserted);
        change.setId(2L);
        when(changedOfferRepository.getById(2L)).thenReturn(change);

        CompanyOffer result = companyOfferService.acceptChange("2");

        assertThat(result.getPricePerHour().floatValue()).isCloseTo(100.0f,
            Percentage.withPercentage(1));
    }

    @Test
    void acceptChangesNoOffer() {
        assertThatThrownBy(() -> companyOfferService.acceptChange("12345")).isInstanceOf(
            OfferNotFoundException.class);
    }

    @Test
    void acceptChangesNaN() {
        assertThatThrownBy(() -> companyOfferService.acceptChange("gabbiano")).isInstanceOf(
            OfferNotFoundException.class);
    }

    @Test
    void acceptOffer() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        when(companyOfferRepository.getById(1L)).thenReturn(inserted);

        assertThat(inserted.isAccepted()).isFalse();

        CompanyOffer updated = companyOfferService.acceptOffer("1");
        assertThat(updated.isAccepted()).isTrue();
    }

    @Test
    void acceptOfferWrongId() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);

        when(companyOfferRepository.getById(1L)).thenReturn(inserted);

        assertThatThrownBy(() -> companyOfferService.acceptOffer("10")).isInstanceOf(
            OfferNotFoundException.class);

        assertThatThrownBy(() -> companyOfferService.acceptOffer("lmao")).isInstanceOf(
            OfferNotFoundException.class);

    }

    @Test
    void getAcceptedOffersFoundOneOutOfTwo() {

        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        companyOffer.setPricePerHour(new BigDecimal(price));

        companyOffer.setAccepted(true);

        // Company offer 2 is not accepted
        CompanyOffer companyOffer2 = new CompanyOffer();

        companyOffer2.setCompanyId(companyName);
        companyOffer2.getExpertise().add(new Expertise(expString));
        companyOffer2.getRequirementsSet().add(new Requirement(reqString));

        companyOffer2.setTotalHours(420);
        companyOffer2.setWeeklyHours(12);

        companyOffer2.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer inserted = companyOfferService.createOffer(companyOffer, postId);
        companyOfferService.createOffer(companyOffer2, postId);

        Set<CompanyOffer> returnSet = new HashSet<>();
        returnSet.add(inserted);
        when(companyOfferRepository.getAllByCompanyIdAndAcceptedIsTrue(companyName))
                .thenReturn(returnSet);
        when(companyOfferRepository.existsByCompanyId(companyName)).thenReturn(true);

        assertThat(companyOfferService.getAcceptedOffers(companyName)).isEqualTo(returnSet);
    }

    @Test
    void getAcceptedOffersNotFound() {
        when(companyOfferRepository.existsByCompanyId(companyName)).thenReturn(false);

        assertThatThrownBy(() -> {
            companyOfferService.getAcceptedOffers(companyName);
        }).isInstanceOf(OfferNotFoundException.class);
    }

    @Test
    void createValidContract() {
        CompanyOffer companyOffer = new CompanyOffer();
        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));
        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);
        companyOffer.setAccepted(true);
        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer offer = companyOfferService.createOffer(companyOffer, postId);

        LocalDate startDate = LocalDate.of(2022, 01, 01);
        LocalDate endDate = LocalDate.of(2022, 02, 01);

        Contract contract = new Contract(post.getAuthor(),
                offer.getCompanyId(), post.getAuthor(), offer.getCompanyId(),
                LocalTime.of(offer.getWeeklyHours().intValue(), 0),
                offer.getPricePerHour().floatValue(), startDate, endDate);

        String url = "http://localhost:7070/contract/create";

        when(restTemplate.postForEntity(
                ArgumentMatchers.eq(url),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(Contract.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(contract);
        when(companyOfferRepository.existsById(1L)).thenReturn(true);
        when(companyOfferRepository.getById(1L)).thenReturn(offer);

        Contract response = companyOfferService.createContract(
                "1", startDate, endDate, restTemplate);
        assertThat(response).isEqualTo(contract);
    }

    @Test
    void createContractOfferNotAccepted() {
        CompanyOffer companyOffer = new CompanyOffer();
        companyOffer.setCompanyId(companyName);
        companyOffer.getExpertise().add(new Expertise(expString));
        companyOffer.getRequirementsSet().add(new Requirement(reqString));
        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);
        // Explicitly set accepted to false (though it is constructed false as default)
        companyOffer.setAccepted(false);
        companyOffer.setPricePerHour(new BigDecimal(price));

        String postId = post.getId().toString();

        CompanyOffer offer = companyOfferService.createOffer(companyOffer, postId);

        LocalDate startDate = LocalDate.of(2022, 01, 01);
        LocalDate endDate = LocalDate.of(2022, 02, 01);

        when(companyOfferRepository.existsById(1L)).thenReturn(true);
        when(companyOfferRepository.getById(1L)).thenReturn(offer);
        assertThatThrownBy(() -> {
            companyOfferService.createContract("1", startDate, endDate, restTemplate);
        }).isInstanceOf(OfferNotAcceptedException.class);
    }

    @Test
    void createContractOfferDoesntExist() {
        LocalDate startDate = LocalDate.of(2022, 01, 01);
        LocalDate endDate = LocalDate.of(2022, 02, 01);

        when(companyOfferRepository.existsById(69L)).thenReturn(false);
        assertThatThrownBy(() -> {
            companyOfferService.createContract("69", startDate, endDate, restTemplate);
        }).isInstanceOf(OfferNotFoundException.class);
    }

    @Test
    void createContractInvalidOfferId() {
        LocalDate startDate = LocalDate.of(2022, 01, 01);
        LocalDate endDate = LocalDate.of(2022, 02, 01);

        assertThatThrownBy(() -> {
            companyOfferService.createContract("bruh", startDate, endDate, restTemplate);
        }).isInstanceOf(OfferNotFoundException.class);

    }
}