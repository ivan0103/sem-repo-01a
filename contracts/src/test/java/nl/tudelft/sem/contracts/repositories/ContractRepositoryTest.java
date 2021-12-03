package nl.tudelft.sem.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.sem.contracts.entities.Contract;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class ContractRepositoryTest {

    private transient Contract contract;

    @Autowired
    private transient ContractRepository underTest;

    @BeforeEach
    void setUpContractRepo() {
        contract = new Contract(
                1,
                2,
                "student",
                "company",
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );

        underTest.save(contract);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void saveTest() {
        assertThat(underTest).isNotNull();
    }

    @Test
    void findContractByIdTest() {
        //when
        Contract testCase = underTest.findContractById(contract.getId());

        //then
        assertThat(testCase).isEqualTo(contract);
    }

}