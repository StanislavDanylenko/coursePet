package stanislav.danylenko.coursepet.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import stanislav.danylenko.coursepet.exception.UserRegistrationException;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity invalidJwtAuthentication(BadCredentialsException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return status(UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity userExists(UserRegistrationException ex, WebRequest request) {
        log.debug("handling UserRegistrationException...");
        return status(CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFound(EntityNotFoundException ex, WebRequest request) {
        log.debug("handling EntityNotFoundException...");
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity entityNotFound(HttpMessageNotWritableException ex, WebRequest request) {
        log.debug("handling HttpMessageNotWritableException...");
        return status(NOT_FOUND).body(ex.getMessage());
    }
}
