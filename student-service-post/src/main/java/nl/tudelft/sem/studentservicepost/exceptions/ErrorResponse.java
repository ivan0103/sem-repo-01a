package nl.tudelft.sem.studentservicepost.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * The type Error response.
 */
public class ErrorResponse {

    private String message;

    private int status;

    private String error;

    private List<String> details;


    /**
     * Instantiates a new Error response.
     *
     * @param message the message
     * @param status  the status
     * @param details the details
     */
    public ErrorResponse(String message, HttpStatus status, List<String> details) {
        this.message = message;
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.details = details;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }
}