package nl.tudelft.sem.contracts.controllers;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.services.ContractService;
import nl.tudelft.sem.contracts.services.DetailsCheckService;
import nl.tudelft.sem.contracts.services.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/contract")
public class ContractController {
    private final transient ContractService contractService;
    private final transient PdfGeneratorService pdfGeneratorService;
    private final transient DetailsCheckService detailsCheckService;

    /**
     * This auto-wires different services to allow this controller class to use their functions.
     *
     * @param contractService     - the functions needed for contracts
     * @param pdfGeneratorService - the functions needed for pdfGeneration of contract
     */
    @Autowired
    public ContractController(ContractService contractService,
                              PdfGeneratorService pdfGeneratorService,
                              DetailsCheckService detailsCheckService) {
        this.contractService = contractService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.detailsCheckService = detailsCheckService;
    }

    /**
     * This method uses contractService to save and return the saved contract.
     *
     * @param contract - the contract to save.
     * @return The saved contract.
     * @throws Exception - if contract details are invalid.
     */
    @PostMapping("/create")
    public ResponseEntity<Contract> create(
        @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Contract contract) throws Exception {

        if (!detailsCheckService.checkContractDates(contract.getStartDate(),
            contract.getEndDate())) {
            throw new Exception("Invalid start or end dates for contract");
        }

        if (!detailsCheckService.checkContractWorkHours(contract.getHoursPerWeek())) {
            throw new Exception("hours per week is more than allowed 20h");
        }
        return new ResponseEntity<>(contractService.create(contract), HttpStatus.CREATED);
    }

    /**
     * This method uses contractService to return a contract given its id.
     *
     * @param contractId - the id of the contract to find.
     * @return The contract.
     * @throws Exception - in case no contract matches the id.
     */
    @PostMapping("/getContract")
    public ResponseEntity<Contract> getContract(@RequestParam long contractId) throws Exception {
        if (!contractService.exists(contractId)) {
            throw new Exception("Invalid contract id. Contract Id does not exist in database.");
        }

        return new ResponseEntity<>(contractService.getContract(contractId), HttpStatus.FOUND);
    }

    /**
     * This method uses contractService and pdfGeneratorService to return a PDF
     * of a contract to the client, given its id.
     *
     * @param response   -the HTTP response to send back to client.
     * @param contractId - the id of contract to return pdf of.
     * @throws Exception - if either id is invalid or there is an IO exception
     *                   during PDF generation.
     */
    @PostMapping("/getPdf")
    public void getContractPdf(HttpServletResponse response,
                               @RequestParam long contractId) throws Exception {
        if (!contractService.exists(contractId)) {
            throw new Exception("Invalid contract id. Contract Id does not exist in database.");
        }
        Contract contract = contractService.getContract(contractId);

        //set response to be a PDF.
        response.setContentType("application/pdf");

        //Get the time to use for file-name.
        LocalDateTime currentDateTime = LocalDateTime.now();

        //Set the header for the HTTP response and filename.
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        //Actually generate the PDF.
        this.pdfGeneratorService.exportContract(response, contract);
    }

    /**
     * Modifies details of an existing contract.
     *
     * @param contract - the contract to change with new details.
     * @return The saved contract
     * @throws Exception - If id of contract does not exist in database or new details are invalid.
     */
    @PostMapping("/modify")
    public Contract modifyContract(@RequestParam Contract contract) throws Exception {
        if (!contractService.exists(contract.getId())) {
            throw new Exception("Invalid contract id. Contract Id does not exist in database.");
        }

        if (!detailsCheckService.checkContractDates(contract.getStartDate(),
            contract.getEndDate())) {
            throw new Exception("Invalid start or end dates for contract");
        }

        if (!detailsCheckService.checkContractWorkHours(contract.getHoursPerWeek())) {
            throw new Exception("hours per week is more than allowed 20h");
        }
        return contractService.create(contract);
    }

}
