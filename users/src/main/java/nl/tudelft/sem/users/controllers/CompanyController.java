package nl.tudelft.sem.users.controllers;

import nl.tudelft.sem.users.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class CompanyController {

    private final transient CompanyService companyService;

    /**
     * This method sets up all the necessary services and is called when the server is started.
     *
     * @param companyService the company service
     */

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

}