package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompanyOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyOfferServiceTest {

    transient Post post;

    @Autowired
    transient CompanyOfferService companyOfferService;

    @Autowired
    transient CompanyOfferRepository companyOfferRepository;

    @Autowired
    transient PostService postService;

    @BeforeEach
    void setup() {
        post = new Post();
        post.setId(0L);
        post.setAuthor("despacito");
        post.setPricePerHour(new BigDecimal("15.0"));
        post.getCompetencySet().add(new Competency("comp1"));
        post.getExpertiseSet().add(new Expertise("exp1"));

        post = postService.createPost(post);
    }

    @Test
    void createOffer() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId("あなたばか");
        companyOffer.getExpertise().add(new Expertise("being not stupid"));
        companyOffer.getRequirementsSet().add(new Requirement("being smort"));

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
        companyOffer.getExpertise().add(new Expertise("being not stupid"));
        companyOffer.getRequirementsSet().add(new Requirement("being smort"));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);

        assertThatThrownBy(() -> companyOfferService.createOffer(companyOffer, "12"))
            .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void createOfferInvalidId() {
        CompanyOffer companyOffer = new CompanyOffer();

        companyOffer.setCompanyId("scemo chi legge");
        companyOffer.getExpertise().add(new Expertise("being not stupid"));
        companyOffer.getRequirementsSet().add(new Requirement("being smort"));

        companyOffer.setTotalHours(420);
        companyOffer.setWeeklyHours(12);


        assertThatThrownBy(() -> companyOfferService.createOffer(companyOffer, "a valid id lol"))
            .isInstanceOf(NumberFormatException.class);
    }

}