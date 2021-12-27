package nl.tudelft.sem.genericservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Student offer was not found in the Generic post!")
public class StudentOfferNotFoundException extends RuntimeException{
    public static final long serialVersionUID = 1L;
}
