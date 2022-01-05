package nl.tudelft.sem.studentservicepost.controllers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.ChangedOffer;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Requirement;
import nl.tudelft.sem.studentservicepost.exceptions.OfferNotFoundException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.services.CompanyOfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class CompanyOfferControllerTest {

    private final transient String baseUrl = "/offers";

    private transient CompanyOffer companyOffer = new CompanyOffer();

    private transient ChangedOffer changedOffer;

    private transient String serializedOffer;
    private transient String serializedChangedOffer;


    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient CompanyOfferService companyOfferService;

    @BeforeEach
    void setup() {
        companyOffer.setId(1L);
        companyOffer.setCompanyId("big money srl");
        companyOffer.setWeeklyHours(10);
        companyOffer.setTotalHours(80);
        companyOffer.setExpertise(Set.of(new Expertise("epxersite")));
        companyOffer.setPricePerHour(new BigDecimal("1.00"));
        companyOffer.setRequirementsSet(Set.of(new Requirement("emacs")));

        changedOffer = new ChangedOffer(companyOffer);

        changedOffer.setPricePerHour(new BigDecimal("100.00"));

        try {
            ObjectMapper om = new ObjectMapper();
            serializedOffer = om.writeValueAsString(companyOffer);
            serializedChangedOffer = om.writeValueAsString(changedOffer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }

        when(companyOfferService.createOffer(any(), any())).thenAnswer(
            AdditionalAnswers.returnsFirstArg());
        when(companyOfferService.getByPostId(anyString())).thenThrow(new PostNotFoundException());
        when(companyOfferService.getByPostId(eq("1"))).thenReturn(Set.of(companyOffer));

        when(companyOfferService.suggestChange(any(CompanyOffer.class))).thenThrow(
            new OfferNotFoundException());

        when(companyOfferService.suggestChange(eq(companyOffer))).thenReturn(changedOffer);

        when(companyOfferService.getChanges(anyString())).thenThrow(new OfferNotFoundException());
        when(companyOfferService.getChanges(eq("1"))).thenReturn(Set.of(changedOffer));

        when(companyOfferService.acceptChange(anyString())).thenThrow(new OfferNotFoundException());

        when(companyOfferService.acceptChange(eq("2"))).thenReturn(changedOffer);

        when(companyOfferService.acceptOffer(anyString())).thenThrow(new OfferNotFoundException());

        when(companyOfferService.acceptOffer(eq("1"))).thenReturn(companyOffer);


    }

    @Test
    void createOffer() {
        String url = baseUrl + "/create?postId=1";
        try {
            this.mockMvc.perform(post(url).content(serializedOffer)
                    .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated());
            verify(companyOfferService).createOffer(companyOffer, "1");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in creating offer");
        }
    }

    @Test
    void getOffers() {
        String url = baseUrl + "/getByPostId?postId=1";
        try {
            this.mockMvc.perform(get(url)).andDo(print())
                .andExpect(status().isOk());
            verify(companyOfferService).getByPostId("1");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in retrieving offer");
        }
    }

    @Test
    void suggestChange() {
        String url = baseUrl + "/suggestChange";
        try {
            this.mockMvc.perform(patch(url).content(serializedOffer)
                    .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
            verify(companyOfferService).suggestChange(companyOffer);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in suggesting change");
        }
    }

    @Test
    void getChangedOffers() {
        String url = baseUrl + "/getChanges?offerId=1";
        try {
            this.mockMvc.perform(get(url)).andDo(print())
                .andExpect(status().isOk());
            verify(companyOfferService).getChanges("1");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in getting changed offers");
        }
    }

    @Test
    void acceptChangedOffer() {
        String url = baseUrl + "/acceptChanges?changedId=2";
        try {
            this.mockMvc.perform(patch(url)).andDo(print())
                .andExpect(status().isOk());
            verify(companyOfferService).acceptChange("2");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in accepting changed offer");
        }
    }

    @Test
    void acceptOffer() {
        String url = baseUrl + "/acceptOffer?offerId=1";
        try {
            this.mockMvc.perform(patch(url)).andDo(print())
                .andExpect(status().isOk());
            verify(companyOfferService).acceptOffer("1");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in accepting offer");
        }
    }

    /**
     * Invalid offer test.
     * Validator is the same for each endpoint, so we test it once
     */
    @Test
    void invalidOffer() {
        String url = baseUrl + "/create?postId=1";
        try {
            this.mockMvc.perform(post(url).content("serializedOffer")
                    .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in creating offer");
        }
    }
}