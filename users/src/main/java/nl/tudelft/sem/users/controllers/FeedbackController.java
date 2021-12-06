package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "feedbacks")
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

    /**
     * GetMapping for feedbacks.
     *
     * @return a list of feedbacks
     */

    @GetMapping
    public List<Feedback> getFeedbacks() {
        return feedbackService.getFeedbacks();
    }

    /**
     * GetMapping for one specific feedback.
     *
     * @param id the id of the feedback we want to find
     * @return the feedback we look for or null
     */

    @GetMapping(path = "{id}")
    public Feedback getOneFeedback(@PathVariable(value = "id") Long id) {
        return feedbackService.getOneFeedback(id);
    }

}
