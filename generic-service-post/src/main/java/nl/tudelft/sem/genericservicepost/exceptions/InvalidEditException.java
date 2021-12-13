package nl.tudelft.sem.genericservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "NetId can't be edited")
public class InvalidEditException extends RuntimeException{
    public static final long serialVersionUID = (long) 2;
}
