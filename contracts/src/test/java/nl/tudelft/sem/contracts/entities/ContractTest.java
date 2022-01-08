package nl.tudelft.sem.contracts.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContractTest {
    private transient Contract contract;

    private transient String student;

    private transient String company;


    @BeforeEach
    void setUpContractTest() {
        student = "student";

        company = "company";

        contract = new Contract(
                "1",
                "2",
                student,
                company,
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );
    }

    @AfterEach
    void resetContractTest() {
        contract = new Contract(
                "1",
                "2",
                student,
                company,
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );
    }

    @Test
    public void contractConstructorTest() {
        assertThat(contract).isNotNull();
    }

    @Test
    public void getFreelancerIdTest() {
        String freelancerId = contract.getFreelancerId();
        assertThat(freelancerId).isEqualTo("1");
    }

    @Test
    public void getCompanyIdTest() {
        String companyId = contract.getCompanyId();
        assertThat(companyId).isEqualTo("2");
    }

    @Test
    public void getFreelancerNameTest() {
        String freelancerName = contract.getFreelancerName();
        assertThat(freelancerName).isEqualTo("student");
    }

    @Test
    public void getCompanyNameTest() {
        String companyName = contract.getCompanyName();
        assertThat(companyName).isEqualTo("company");
    }

    @Test
    public void getHoursPerWeekTest() {
        LocalTime hoursPerWeek = contract.getHoursPerWeek();
        assertThat(hoursPerWeek).isEqualTo(LocalTime.of(6, 0));
    }

    @Test
    public void getPayPerWeekTest() {
        float payPerWeek = contract.getPayPerWeek();
        assertThat(payPerWeek).isEqualTo(14.5F);
    }

    @Test
    public void getStartDateTest() {
        LocalDate startDate = contract.getStartDate();
        assertThat(startDate).isEqualTo(LocalDate.of(2020, 6, 1));
    }

    @Test
    public void getEndDateTest() {
        LocalDate endDate = contract.getEndDate();
        assertThat(endDate).isEqualTo(LocalDate.of(2020, 9, 1));
    }

    @Test
    public void setFreelancerIdTest() {
        String id = "3";
        contract.setFreelancerId(id);
        String freelancerId = contract.getFreelancerId();
        assertThat(freelancerId).isEqualTo(id);
    }

    @Test
    public void setCompanyIdTest() {
        String id = "4";
        contract.setCompanyId(id);
        String companyId = contract.getCompanyId();
        assertThat(companyId).isEqualTo(id);
    }

    @Test
    public void setFreelancerNameTest() {
        contract.setFreelancerName("student1");
        String freelancerName = contract.getFreelancerName();
        assertThat(freelancerName).isEqualTo("student1");
    }

    @Test
    public void setCompanyNameTest() {
        contract.setCompanyName("company1");
        String companyName = contract.getCompanyName();
        assertThat(companyName).isEqualTo("company1");
    }

    @Test
    public void setHoursPerWeekTest() {
        contract.setHoursPerWeek(LocalTime.of(6, 30));
        LocalTime hoursPerWeek = contract.getHoursPerWeek();
        assertThat(hoursPerWeek).isEqualTo(LocalTime.of(6, 30));
    }

    @Test
    public void setPayPerWeekTest() {
        contract.setPayPerWeek(13F);
        float payPerWeek = contract.getPayPerWeek();
        assertThat(payPerWeek).isEqualTo(13F);
    }

    @Test
    public void setStartDateTest() {
        contract.setStartDate(LocalDate.of(2020, 6, 3));
        LocalDate startDate = contract.getStartDate();
        assertThat(startDate).isEqualTo(LocalDate.of(2020, 6, 3));
    }

    @Test
    public void setEndDateTest() {
        contract.setEndDate(LocalDate.of(2020, 10, 1));
        LocalDate endDate = contract.getEndDate();
        assertThat(endDate).isEqualTo(LocalDate.of(2020, 10, 1));
    }

    @Test
    public void setAgreementDateTest() {
        contract.setAgreementDate(LocalDate.of(2019, 10, 1));
        LocalDate agreementDate = contract.getAgreementDate();
        assertThat(agreementDate).isEqualTo(LocalDate.of(2019, 10, 1));
    }

    @Test
    public void equalFalseTest() {
        Contract contract1 = new Contract(
                "1",
                "2",
                student,
                company,
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );
        contract.setId(1);
        contract1.setId(2);

        assertThat(contract.equals(contract1)).isFalse();
    }

    @Test
    public void equalTrue() {
        assertThat(contract).isEqualTo(contract);
    }

    @Test
    public void equalWrongObject() {
        assertThat(contract.equals(student)).isFalse();
    }

    @Test
    public void hashCodeTest() {
        int expected = Objects.hash(contract.getId());
        assertThat(contract.hashCode()).isEqualTo(expected);
    }

    @Test
    public void getAgreementDate() {
        assertThat(contract.getAgreementDate()).isEqualTo(LocalDate.now());
    }
}
