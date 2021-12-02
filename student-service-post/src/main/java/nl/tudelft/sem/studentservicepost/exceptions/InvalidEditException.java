package nl.tudelft.sem.studentservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "NetID can't be edited")

public class InvalidEditException extends RuntimeException{
}
