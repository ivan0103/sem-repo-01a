package nl.tudelft.sem.users.controllers;

import nl.tudelft.sem.users.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "feedback")
public class FeedbackController {

    private final transient FeedbackService feedbackService;

    /**
     * This method sets up all the necessary services and is called when the server is started.
     *
     * @param feedbackService the feedback service
     */

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

}
