package nl.tudelft.sem.contracts.controllers;

import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.services.ContractService;
import nl.tudelft.sem.contracts.services.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/contract")
public class ContractController {
    private ContractService contractService;
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    public ContractController(ContractService contractService, PdfGeneratorService pdfGeneratorService) {
        this.contractService = contractService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping("/create")
    Contract create(@RequestParam Contract contract) throws Exception {
        return contractService.create(contract);
    }

    @PostMapping("/getContract")
    Contract getContract(@RequestParam long contractId) throws Exception {
        Contract contract = contractService.getContract(contractId);
        if (contract == null) {
            throw new Exception("Invalid contract id.");
        }

        return contract;
    }

    @PostMapping("/getPdf")
    public void getContractPdf(HttpServletResponse response,
                               @RequestParam long contractId) throws Exception {
        Contract contract = contractService.getContract(contractId);
        if (contract == null) {
            throw new Exception("Invalid contract id.");
        }

        response.setContentType("application/pdf");
        LocalDateTime currentDateTime = LocalDateTime.now();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime.toString() +".pdf";
        response.setHeader(headerKey, headerValue);
        this.pdfGeneratorService.exportContract(response, contract);
    }


    //@PostMapping("/modify")

}
