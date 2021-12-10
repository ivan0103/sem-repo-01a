package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "users")
public class UserController {

    private final transient String valueId = "netID";
    private final transient String valueName = "name";
    private final transient String receiverId = "toNetID";
    private final transient UserService userService;

    /**
     * This method sets up all the necessary services and is called when the server is started.
     *
     * @param userService the user service
     */

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GetMapping for users.
     *
     * @return a list of users
     */

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * GetMapping for one specific user.
     *
     * @param netID the id of the user we want to find
     * @return the user we look for or null
     */

    @GetMapping(path = "{" + valueId + "}")
    public User getOneUser(@PathVariable(value = valueId) String netID) {
        return userService.getOneUser(netID);
    }

    /**
     * PostMapping for one specific user.
     *
     * @param netID the id of the new user
     * @param name the name of the new user
     * @return a new user
     */

    @PostMapping(path = "{" + valueId + "}/{" + valueName + "}/{role}")
    public User addUser(@PathVariable(value = valueId) String netID,
                        @PathVariable(value = valueName) String name,
                        @PathVariable(value = "role") String role) {

        return userService.addUser(netID, name, role);
    }

    /**
     * Post mapping for adding feedback.
     *
     * @param netID the id of the user
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

        return userService.addFeedback(netID, text, rating, toNetID);
    }

    /**
     * DeleteMapping for user.
     *
     * @param netID the id of the user
     * @return the user that was deleted
     */

    @DeleteMapping(path = "{" + valueId + "}")
    public User deleteUser(@PathVariable(value = valueId) String netID) {
        return userService.deleteUser(netID);
    }

    /**
     * PutMapping for user.
     *
     * @param netID the id of the user
     * @param name the new name of the user
     * @return the user with updated name
     */

    @PutMapping(path = "{" + valueId + "}/{" + valueName + "}")

    public User updateUser(@PathVariable(value = "netID") String netID,
                              @PathVariable(value = "name") String name) {

        return userService.updateUser(netID, name);
    }

    /**
     * PutMapping for feedback.
     *
     * @param netID the id of the user
     * @param text the new text of the feedback
     * @param rating the new rating of the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the user that received the feedback
     * @return an edited feedback
     */

    @PutMapping(path = "{" + valueId + "}/{text}/{rating}/{feedbackId}/{toNetID}")
    public Feedback editFeedback(@PathVariable(value = valueId) String netID,
                                 @PathVariable(value = "text", required = false) String text,
                                 @PathVariable(value = "rating", required = false) Integer rating,
                                 @PathVariable(value = "feedbackId") Long feedbackId,
                                 @PathVariable(value = "toNetID") String toNetID) {

        return userService.editFeedback(netID, text, rating, feedbackId, toNetID);
    }

    /**
     * DeleteMapping for feedback.
     *
     * @param netID the id of the user that created the feedback
     * @param feedbackId the id of the feedback
     * @param toNetID the id of the feedback receiver
     * @return the deleted feedback
     */

    @DeleteMapping(path = "{" + valueId + "}/{feedbackId}/{" + receiverId + "}")
    public Feedback deleteFeedback(@PathVariable(value = valueId) String netID,
                                   @PathVariable(value = "feedbackId") Long feedbackId,
                                   @PathVariable(value = receiverId) String toNetID) {

        return userService.deleteFeedback(netID, feedbackId, toNetID);
    }
}
