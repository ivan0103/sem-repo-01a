package nl.tudelft.sem.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.repositories.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {
    private transient Contract contract;

    private transient ContractService underTest;

    @Mock
    private transient ContractRepository contractRepository;

    //setup
    private transient ArgumentCaptor<Long> idArgumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ContractService(contractRepository);

        contract = new Contract(
                "1",
                "2",
                "student",
                "company",
                LocalTime.of(6, 0),
                14.5F,
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 9, 1)
        );

        idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    }

    @Test
    void createTest() {
        //setup
        ArgumentCaptor<Contract> contractArgumentCaptor = ArgumentCaptor.forClass(Contract.class);

        //given
        given(contractRepository.save(contract)).willReturn(contract);

        //when
        Contract testCase = underTest.create(contract);

        //then
        verify(contractRepository).save(contractArgumentCaptor.capture());
        Contract capturedContract = contractArgumentCaptor.getValue();

        assertThat(capturedContract).isEqualTo(contract);
        assertThat(testCase).isEqualTo(contract);
    }

    @Test
    void getContractTest() {
        //given
        given(contractRepository.getById(contract.getId())).willReturn(contract);

        //when
        Contract testCase = underTest.getContract(contract.getId());

        //then
        verify(contractRepository).getById(idArgumentCaptor.capture());
        long capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(contract.getId());
        assertThat(testCase).isEqualTo(contract);
    }

    @Test
    void existsTest() {
        //given
        given(contractRepository.existsById(contract.getId())).willReturn(true);

        //when
        boolean testCase = underTest.exists(contract.getId());

        //then
        verify(contractRepository).existsById(idArgumentCaptor.capture());
        long capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(contract.getId());
        assertThat(testCase).isEqualTo(true);
    }

}