package nl.tudelft.sem.studentservicepost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post ID not found")
public class PostNotFoundException extends RuntimeException {

}
