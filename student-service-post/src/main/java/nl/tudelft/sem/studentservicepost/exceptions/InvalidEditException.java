package nl.tudelft.sem.studentservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "NetID can't be edited")

public class InvalidEditException extends RuntimeException {

    public static final long serialVersionUID = 2L;

}
