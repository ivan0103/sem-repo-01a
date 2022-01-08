package nl.tudelft.sem.genericservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Net ID was not found!")
public class UserNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;
}