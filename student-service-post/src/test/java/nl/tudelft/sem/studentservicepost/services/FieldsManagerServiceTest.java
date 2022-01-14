package nl.tudelft.sem.studentservicepost.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import nl.tudelft.sem.studentservicepost.repositories.RequirementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FieldsManagerServiceTest {

    final transient String requirement1 = "req1";

    final transient String expertise1 = "exp1";

    transient CompanyOffer companyOffer1;
    transient CompanyOffer companyOffer2;

    @InjectMocks
    transient FieldsManagerService fieldsManagerService;

    @Mock
    transient PostRepository postRepository;
    @Mock
    transient RequirementRepository requirementRepository;
    @Mock
    transient ExpertiseRepository expertiseRepository;

    @BeforeEach
    void setup() {
        when(postRepository.existsById(anyLong())).thenReturn(false);
        when(postRepository.existsById(eq(1L))).thenReturn(true);
        when(postRepository.getPostById(eq(1L))).thenReturn(new Post());

        when(requirementRepository.existsById(anyString())).thenReturn(false);
        when(requirementRepository.existsById(eq(requirement1))).thenReturn(true);
        when(requirementRepository.getRequirementByRequirementString(eq(requirement1))).thenReturn(
            new Requirement(requirement1));

        when(expertiseRepository.existsById(anyString())).thenReturn(false);
        when(expertiseRepository.existsById(eq(expertise1))).thenReturn(true);
        when(expertiseRepository.getExpertiseByExpertiseString(eq(expertise1))).thenReturn(
            new Expertise(expertise1));

        companyOffer1 = new CompanyOffer();

        companyOffer1.setCompanyId("company1");
        companyOffer1.getExpertise().add(new Expertise(expertise1));
        companyOffer1.getRequirementsSet().add(new Requirement(requirement1));

        companyOffer1.setTotalHours(420);
        companyOffer1.setWeeklyHours(12);

        companyOffer2 = new CompanyOffer();

        companyOffer2.setCompanyId("company2");
        companyOffer2.getExpertise().add(new Expertise("exp2"));
        companyOffer2.getRequirementsSet().add(new Requirement("req2"));

        companyOffer2.setTotalHours(69);
        companyOffer2.setWeeklyHours(3);
    }

    @Test
    void updateExpertiseNotPresent() {
        fieldsManagerService.updateExpertise(companyOffer2);
        verify(expertiseRepository, never()).getExpertiseByExpertiseString(anyString());
    }

    @Test
    void updateExpertisePresent() {
        fieldsManagerService.updateExpertise(companyOffer1);
        verify(expertiseRepository, times(1)).getExpertiseByExpertiseString(expertise1);
    }

    @Test
    void updateRequirementNotPresent() {
        fieldsManagerService.updateRequirement(companyOffer2);
        verify(requirementRepository, never()).getRequirementByRequirementString(anyString());
    }

    @Test
    void updateRequirementPresent() {
        fieldsManagerService.updateRequirement(companyOffer1);
        verify(requirementRepository, times(1)).getRequirementByRequirementString(requirement1);
    }

    @Test
    void updatePostNotPresent() {
        assertThat(fieldsManagerService.updatePost(companyOffer2, "12")).isFalse();
    }

    @Test
    void updatePostPresent() {
        assertThat(fieldsManagerService.updatePost(companyOffer1, "1")).isTrue();
    }
}