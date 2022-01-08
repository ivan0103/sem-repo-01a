//package nl.tudelft.sem.contracts.integration;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import nl.tudelft.sem.contracts.entities.Contract;
//import nl.tudelft.sem.contracts.services.ContractService;
//import nl.tudelft.sem.contracts.services.DetailsCheckService;
//import nl.tudelft.sem.contracts.services.PdfGeneratorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureMockMvc
//public class ContractControllerIntegrationTest {
//
//    private final transient String baseUrl = "/offers";
//
//    private transient Contract contract;
//
//    @MockBean
//    private final transient ContractService contractService;
//
//    @MockBean
//    private final transient PdfGeneratorService pdfGeneratorService;
//
//    @MockBean
//    private final transient DetailsCheckService detailsCheckService;
//
//    @Autowired
//    private  transient MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        contract = new Contract(
//                "1",
//                "2",
//                "student",
//                "company",
//                LocalTime.of(6, 0),
//                14.5F,
//                LocalDate.of(2020, 6, 1),
//                LocalDate.of(2020, 9, 1)
//        );
//
//    }
//}
