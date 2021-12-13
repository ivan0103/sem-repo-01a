package nl.tudelft.sem.genericservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Generic post ID was not found!")
public class GenericPostNotFoundException extends RuntimeException{
    public static final long serialVersionUID = (long) 1;
}
