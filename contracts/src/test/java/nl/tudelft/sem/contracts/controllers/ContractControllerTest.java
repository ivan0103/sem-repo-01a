package nl.tudelft.sem.contracts.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.http.HttpServletResponse;
import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.services.ContractService;
import nl.tudelft.sem.contracts.services.PdfGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ContractControllerTest {
    Contract contract;

    private ContractController underTest;

    @Mock
    private ContractService contractService;

    @Mock
    private PdfGeneratorService pdfGeneratorService;

    ArgumentCaptor<Contract> contractArgumentCaptor;
    ArgumentCaptor<Long> idArgumentCaptor;


    @BeforeEach
    void setUp() {
        underTest = new ContractController(contractService, pdfGeneratorService);
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

        contractArgumentCaptor = ArgumentCaptor.forClass(Contract.class);
        idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    }

    @Test
    void saveTest() throws Exception {
        //given
        given(contractService.create(contract)).willReturn(contract);

        //when
        Contract testCase = underTest.create(contract);

        //then
        verify(contractService).create(contractArgumentCaptor.capture());
        Contract capturedContract = contractArgumentCaptor.getValue();

        assertThat(capturedContract).isEqualTo(contract);
        assertThat(testCase).isEqualTo(contract);
    }

    @Test
    void getContractPdfTest() throws Exception {
        //given
        given(contractService.getContract(contract.getId())).willReturn(contract);

        //setup
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<HttpServletResponse> responseArgumentCaptor =
                ArgumentCaptor.forClass(HttpServletResponse.class);

        //when
        underTest.getContractPdf(response, contract.getId());

        //then
        verify(contractService).getContract(idArgumentCaptor.capture());
        verify(pdfGeneratorService).exportContract(responseArgumentCaptor.capture(),
                contractArgumentCaptor.capture());

        long capturedId = idArgumentCaptor.getValue();
        HttpServletResponse capturedResponse = responseArgumentCaptor.getValue();
        Contract capturedContract = contractArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(contract.getId());
        assertThat(capturedContract).isEqualTo(contract);
        assertThat(capturedResponse).isEqualTo(response);
    }

    @Test
    void getContractTest() throws Exception {
        //given
        given(contractService.getContract(contract.getId())).willReturn(contract);


        //when
        Contract result = underTest.getContract(contract.getId());

        //then
        verify(contractService).getContract(idArgumentCaptor.capture());
        long capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(contract.getId());
        assertThat(result).isEqualTo(contract);
    }
}