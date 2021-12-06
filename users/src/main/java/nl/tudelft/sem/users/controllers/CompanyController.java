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

        return companyService.addUser(netID, netID);
    }

    /**
     * Post mapping for adding feedback.
     *
     * @param netID the id of the company
     * @param text the text of the feedback
     * @param rating the rating of the feedback
     * @param toNetID the netID of the user that receives the feedback
     * @return a new feedback
     */

    @PostMapping(path = "{" + valueId + "}/{text}/{rating}/{toNetID}")
    public Feedback addFeedback(@PathVariable(value = valueId) String netID,
                                @PathVariable(value = "text") String text,
                                @PathVariable(value = "rating") Integer rating,
                                @PathVariable(value = "toNetID") String toNetID) {

        return companyService.addFeedback(netID, text, rating, toNetID);
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
     * @return the company with updated name
     */

    @PutMapping(path = "{" + valueId + "}/{" + valueName + "}")

    public Company updateUser(@PathVariable(value = valueId) String netID,
                              @PathVariable(value = valueName) String name) {

        return companyService.updateUser(netID, name);
    }

    /**
     * PutMapping for feedback.
     *
     * @param netID the id of the company
     * @param text the new text of the feedback
     * @param rating the new rating of the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the company that received the feedback
     * @return an edited feedback
     */

    @PutMapping(path = "{" + valueId + "}/{text}/{rating}/{feedbackId}/{toNetID}")
    public Feedback editFeedback(@PathVariable(value = valueId) String netID,
                                 @PathVariable(value = "text", required = false) String text,
                                 @PathVariable(value = "rating", required = false) Integer rating,
                                 @PathVariable(value = "feedbackId") Long feedbackId,
                                 @PathVariable(value = "toNetID") String toNetID) {

        return companyService.editFeedback(netID, text, rating, feedbackId, toNetID);
    }

    /**
     * DeleteMapping for feedback.
     *
     * @param netID the id of the company that created the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the feedback receiver
     * @return the deleted feedback
     */

    @DeleteMapping(path = "{" + valueId + "}/{feedbackId}/{" + receiverId + "}")
    public Feedback deleteFeedback(@PathVariable(value = valueId) String netID,
                                   @PathVariable(value = "feedbackId") Long feedbackId,
                                   @PathVariable(value = receiverId) String toNetID) {

        return companyService.deleteFeedback(netID, feedbackId, toNetID);
    }
}