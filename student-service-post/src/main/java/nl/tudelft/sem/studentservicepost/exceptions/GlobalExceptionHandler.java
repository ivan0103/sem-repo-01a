package nl.tudelft.sem.studentservicepost.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<String> errorDetails = new ArrayList<>();
        for (ObjectError e : ex.getBindingResult().getAllErrors()) {
            errorDetails.add(e.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Object validation failed", status, errorDetails);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle post not found.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handlePostNotFound(
            PostNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                "Post does not exist",
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle invalid edit of post NetID.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(InvalidEditException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<Object> handleInvalidEdit(
            InvalidEditException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                "NetID cannot be edited",
                HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleOfferNotFound(
            OfferNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
                "Offer does not exist",
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle all uncaught exceptions.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtExceptions(
            RuntimeException exception,
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                "Oops! Something went wrong!",
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
