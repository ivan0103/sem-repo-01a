package nl.tudelft.sem.studentservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Offer ID not found")
public class OfferNotFoundException extends RuntimeException {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 3L;
}
