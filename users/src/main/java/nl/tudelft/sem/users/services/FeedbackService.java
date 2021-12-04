package nl.tudelft.sem.users.services;

import java.util.List;
import nl.tudelft.sem.users.entities.Feedback;
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

    /**
     * Getter for all the feedbacks in the database.
     *
     * @return a list of feedbacks contained in the database
     */

    public List<Feedback> getFeedbacks() {
        return feedbackRepository.findAll();
    }

    /**
     * Retrieves only one feedback based on the id.
     *
     * @param id - the id of the feedback to be retrieved
     * @return the feedback
     */

    public Feedback getOneFeedback(Long id) {
        if (feedbackRepository.findById(id).isEmpty()) {
            return null;
        }

        return feedbackRepository.findById(id).get();
    }
}
