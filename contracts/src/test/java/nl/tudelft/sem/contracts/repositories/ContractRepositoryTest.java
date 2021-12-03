package nl.tudelft.sem.contracts.repositories;

import nl.tudelft.sem.contracts.entities.Contract;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ContractRepositoryTest {

    Contract contract;

    @Autowired
    ContractRepository underTest;

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
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void saveTest() {
        //when
        Contract testCase = underTest.save(contract);

        //then
        assertThat(testCase).isEqualTo(contract);
        assertThat(underTest).isNotNull();
    }

    @Test
    void findContractByIdTest() {
        //when
        underTest.save(contract);
        Contract testCase = underTest.findContractById(contract.getId());

        //then
        assertThat(testCase).isEqualTo(contract);
    }

}