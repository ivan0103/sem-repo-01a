package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContractTest {

    private transient Contract contract1;
    private transient Contract contract2;
    private transient Contract contract3;

    @Spy
    private transient ContractTimes contractTimes = new ContractTimes();

    @Spy
    private transient ContractParties contractParties = new ContractParties();

    private final transient String freelancerId2 = "freelancerId";
    private final transient String companyId2 = "companyId";
    private final transient String freelancerName2 = "name";
    private final transient String companyName2 = "company";
    private final transient LocalTime hoursPerWeek2 = LocalTime.of(7, 0);
    private final transient float pay2 = 12.0f;
    private final transient LocalDate start2 = LocalDate.of(2021, 12, 31);
    private final transient LocalDate end2 = LocalDate.of(2022, 2, 1);
    private final transient LocalDate agreement2 = LocalDate.of(2021, 12, 30);


    @BeforeEach
    void setUp() {

        contract1 = new Contract();

        contract1.setId(1L);

        contract2 =
            new Contract(freelancerId2, companyId2, freelancerName2, companyName2, hoursPerWeek2,
                pay2, start2, end2, contractParties, contractTimes, agreement2);

        contract2.setId(2L);

        contract3 =
            new Contract(freelancerId2, companyId2, freelancerName2, companyName2, hoursPerWeek2,
                pay2, start2, end2);

        contract3.setId(2L);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        assertThat(contract1.getId()).isEqualTo(1L);
    }

    @Test
    void setId() {
        contract1.setId(10L);
        assertThat(contract1.getId()).isEqualTo(10L);
    }

    @Test
    void getFreelancerId() {
        assertThat(contract2.getFreelancerId()).isEqualTo(freelancerId2);
        verify(contractParties, times(1)).getFreelancerId();
    }

    @Test
    void setFreelancerId() {
        contract2.setFreelancerId("temp");
        assertThat(contract2.getFreelancerId()).isEqualTo("temp");
        verify(contractParties, times(1)).setFreelancerId("temp");

    }

    @Test
    void getCompanyId() {
        assertThat(contract2.getCompanyId()).isEqualTo(companyId2);
        verify(contractParties, times(1)).getCompanyId();

    }

    @Test
    void setCompanyId() {
        contract2.setCompanyId("comp");
        assertThat(contract2.getCompanyId()).isEqualTo("comp");
        verify(contractParties, times(1)).setCompanyId("comp");

    }

    @Test
    void getFreelancerName() {
        assertThat(contract2.getFreelancerName()).isEqualTo(freelancerName2);
        verify(contractParties, times(1)).getFreelancerName();
    }

    @Test
    void setFreelancerName() {
        contract2.setFreelancerName("despacito");
        assertThat(contract2.getFreelancerName()).isEqualTo("despacito");
        verify(contractParties, times(1)).setFreelancerName("despacito");

    }

    @Test
    void getCompanyName() {
        assertThat(contract2.getCompanyName()).isEqualTo(companyName2);
        verify(contractParties, times(1)).getCompanyName();
    }

    @Test
    void setCompanyName() {
        contract2.setCompanyName("new name");
        assertThat(contract2.getCompanyName()).isEqualTo("new name");
        verify(contractParties, times(1)).setCompanyName("new name");
    }

    @Test
    void getHoursPerWeek() {
        assertThat(contract2.getHoursPerWeek()).isEqualTo(hoursPerWeek2);
        verify(contractTimes, times(1)).getHoursPerWeek();
    }

    @Test
    void setHoursPerWeek() {
        contract2.setHoursPerWeek(LocalTime.of(1, 0));
        assertThat(contract2.getHoursPerWeek()).isEqualTo(LocalTime.of(1, 0));
        verify(contractTimes, times(1)).setHoursPerWeek(LocalTime.of(1, 0));
    }

    @Test
    void getPayPerWeek() {
        assertThat(contract2.getPayPerWeek()).isEqualTo(pay2);
    }

    @Test
    void setPayPerWeek() {
        contract2.setPayPerWeek(15.0f);
        assertThat(contract2.getPayPerWeek()).isEqualTo(15.0f);
    }

    @Test
    void getStartDate() {
        assertThat(contract2.getStartDate()).isEqualTo(start2);
        verify(contractTimes, times(1)).getStartDate();
    }

    @Test
    void setStartDate() {
        contract2.setStartDate(LocalDate.of(2001, 1, 1));
        assertThat(contract2.getStartDate()).isEqualTo(LocalDate.of(2001, 1, 1));
        verify(contractTimes, times(1)).setStartDate(LocalDate.of(2001, 1, 1));
    }

    @Test
    void getEndDate() {
        assertThat(contract2.getEndDate()).isEqualTo(end2);
        verify(contractTimes, times(1)).getEndDate();
    }

    @Test
    void setEndDate() {
        contract2.setEndDate(LocalDate.of(2001, 1, 1));
        assertThat(contract2.getEndDate()).isEqualTo(LocalDate.of(2001, 1, 1));
        verify(contractTimes, times(1)).setEndDate(LocalDate.of(2001, 1, 1));
    }

    @Test
    void getAgreementDate() {
        assertThat(contract2.getAgreementDate()).isEqualTo(agreement2);
        verify(contractTimes, times(1)).getAgreementDate();
    }

    @Test
    void setAgreementDate() {
        contract2.setAgreementDate(LocalDate.of(2001, 1, 1));
        assertThat(contract2.getAgreementDate()).isEqualTo(LocalDate.of(2001, 1, 1));
        verify(contractTimes, times(1)).setAgreementDate(LocalDate.of(2001, 1, 1));
    }

    @Test
    void buildFromOffer() {

        CompanyOffer companyOffer = Mockito.spy(new CompanyOffer());
        Post post = Mockito.spy(new Post());

        companyOffer.setPost(post);

        post.setAuthor(freelancerId2);

        companyOffer.setCompanyId(companyId2);
        companyOffer.setWeeklyHours(hoursPerWeek2.getHour());
        companyOffer.setPricePerHour(new BigDecimal(Double.toString(pay2)));

        Contract returned = Contract.buildFromOffer(companyOffer, start2, end2);

        verify(companyOffer, times(2)).getCompanyId();
        verify(companyOffer, times(1)).getPricePerHour();
        verify(companyOffer, times(1)).getWeeklyHours();
        verify(post, times(2)).getAuthor();

        assertThat(returned.getPayPerWeek()).isEqualTo(pay2);
        assertThat(returned.getHoursPerWeek()).isEqualTo(hoursPerWeek2);
        assertThat(returned.getCompanyName()).isEqualTo(companyId2);
        assertThat(returned.getCompanyId()).isEqualTo(companyId2);
        assertThat(returned.getFreelancerName()).isEqualTo(freelancerId2);
        assertThat(returned.getFreelancerId()).isEqualTo(freelancerId2);
        assertThat(returned.getStartDate()).isEqualTo(start2);
        assertThat(returned.getEndDate()).isEqualTo(end2);

    }

    @Test
    void testEqualsNull() {
        assertThat(contract2).isNotEqualTo(null);
    }

    @Test
    void testEqualsDifferentClass() {
        assertThat(contract2).isNotEqualTo(new Expertise("test"));
    }

    @Test
    void testEqualsSame() {
        assertThat(contract2).isEqualTo(contract2);
    }

    @Test
    void testEqualsDifferent() {
        assertThat(contract2).isNotEqualTo(contract1);
    }

    @Test
    void testEqualsReallyEquals() {
        assertThat(contract2).isEqualTo(contract3);
    }

    @Test
    void testEqualsOnlyId() {
        contract1.setId(contract2.getId());
        assertThat(contract1).isEqualTo(contract2);
    }

    @Test
    void testHashCodeSame() {
        assertThat(contract2.hashCode()).isEqualTo(contract2.hashCode());
    }

    @Test
    void testHashCodeReallyEquals() {
        assertThat(contract2.hashCode()).isEqualTo(contract3.hashCode());
    }

    @Test
    void testHashCodeDifferent() {
        assertThat(contract2.hashCode()).isNotEqualTo(contract1.hashCode());
    }

    @Test
    void testToString() {
        String expected =
            "Contract{" + "id=" + contract2.getId() + ", payPerWeek=" + contract2.getPayPerWeek()
            + ", contractParties=" + contractParties.toString() + ", contractTimes="
            + contractTimes.toString() + '}';
        assertThat(contract2.toString()).isEqualTo(expected);
    }
}