package nl.tudelft.sem.users.services;

import nl.tudelft.sem.users.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final transient FeedbackRepository feedbackRepository;

    /**
     * Constructor of FeedbackService - It instantiates a new FeedbackService object.
     *
     * @param feedbackRepository repository injected with data from the database
     */

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
}
