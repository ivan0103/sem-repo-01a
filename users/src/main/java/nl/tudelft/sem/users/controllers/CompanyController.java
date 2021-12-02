package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping(path = "{" + valueId + "}")
    public Company getOneUser(@PathVariable(value = valueId) String netID) {
        return companyService.getOneUser(netID);
    }

    /**
     * PostMapping for one specific company.
     *
     * @param netID the id of the new company
     * @param name the name of the new company
     * @return a new company
     */

    @PostMapping(path = "{" + valueId + "}/{" + valueName + "}")
    public Company addUser(@PathVariable(value = valueId) String netID,
                           @PathVariable(value = valueName) String name) {

        return companyService.addUser(netID, name);
    }

    /**
     * Post mapping for adding feedback.
     *
     * @param netID the id of the company
     * @param text the text of the feedback
     * @param rating the rating of the feedback
     * @return a new feedback
     */

    @PostMapping(path = "{" + valueId + "}/{text}/{rating}")
    public Feedback addFeedback(@PathVariable(value = valueId) String netID,
                                @PathVariable(value = "text") String text,
                                @PathVariable(value = "rating") Integer rating) {

        return companyService.addFeedback(netID, text, rating);
    }

    /**
     * DeleteMapping for company.
     *
     * @param netID the id of the company
     * @return the company that was deleted
     */

    @DeleteMapping(path = "{" + valueId + "}")
    public Company deleteUser(@PathVariable(value = valueId) String netID) {
        return companyService.deleteUser(netID);
    }

    /**
     * PutMapping for company.
     *
     * @param netID the id of the company
     * @param name the new name of the company
     * @param newNetID the new id of the company (optional and not required for company)
     * @return the company with updated name
     */

    @PutMapping(path = {"{" + valueId + "}/{" + valueName + "}",
            "{" + valueId + "}/{" + valueName + "}/{newNetID}"})

    public Company updateUser(@PathVariable(value = valueId) String netID,
                              @PathVariable(value = valueName) String name,
                              @PathVariable(value = "newNetID", required = false) String newNetID) {

        return companyService.updateUser(netID, name, newNetID);
    }
}