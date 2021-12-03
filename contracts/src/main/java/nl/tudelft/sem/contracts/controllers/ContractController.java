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

    /**
     * This auto-wires different services to allow this controller class to use their functions.
     * @param contractService - the functions needed for contracts
     * @param pdfGeneratorService - the functions needed for pdfGeneration of contract
     */
    @Autowired
    public ContractController(ContractService contractService, PdfGeneratorService pdfGeneratorService) {
        this.contractService = contractService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    /**
     * This method uses contractService to save and return the saved contract.
     * @param contract - the contract to save.
     * @return The saved contract.
     * @throws Exception - in case contract details are invalid.
     */
    @PostMapping("/create")
    Contract create(@RequestParam Contract contract) throws Exception {
        return contractService.create(contract);
    }

    /**
     * This method uses contractService to return a contract given its id.
     * @param contractId - the id of the contract to find.
     * @return The contract.
     * @throws Exception - in case no contract matches the id.
     */
    @PostMapping("/getContract")
    Contract getContract(@RequestParam long contractId) throws Exception {
        Contract contract = contractService.getContract(contractId);
        if (contract == null) {
            throw new Exception("Invalid contract id.");
        }

        return contract;
    }

    /**
     * This method uses contractService and pdfGeneratorService to return a PDF of a contract to the client, given its id.
     * @param response -the HTTP response to send back to client.
     * @param contractId - the id of contract to return pdf of.
     * @throws Exception - if either id is invalid or there is an IO exception during PDF generation.
     */
    @PostMapping("/getPdf")
    public void getContractPdf(HttpServletResponse response,
                               @RequestParam long contractId) throws Exception {
        Contract contract = contractService.getContract(contractId);
        if (contract == null) {
            throw new Exception("Invalid contract id.");
        }

        //set response to be a PDF.
        response.setContentType("application/pdf");

        //Get the time to use for file-name.
        LocalDateTime currentDateTime = LocalDateTime.now();

        //Set the header for the HTTP response and filename.
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime.toString() +".pdf";
        response.setHeader(headerKey, headerValue);

        //Actually generate the PDF.
        this.pdfGeneratorService.exportContract(response, contract);
    }


    //@PostMapping("/modify")

}
