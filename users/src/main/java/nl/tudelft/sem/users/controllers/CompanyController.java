package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "companies")
public class CompanyController implements UserController<Company> {

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

    /**
     * GetMapping for companies.
     *
     * @return a list of companies
     */

    @GetMapping
    public List<Company> getUsers() {
        return companyService.getUsers();
    }

    /**
     * GetMapping for one specific company.
     *
     * @param netID the id of the company we want to find
     * @return the company we look for or null
     */

    @GetMapping(path = "{netID}")
    public Company getOneUser(@PathVariable(value = "netID") String netID) {
        return companyService.getOneUser(netID);
    }
}