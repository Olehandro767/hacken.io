package io.hacken.task.configuration;

import io.hacken.task.exception.FieldDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericHandler(Exception exception) {
        log.error("Smth went wrong", exception);
        return badRequest().body("Something went wrong. Time at server ");
    }

    @ExceptionHandler(FieldDoesNotExistException.class)
    public ResponseEntity<String> fieldDoesNotExistExceptionHandler(FieldDoesNotExistException exception) {
        log.error("field doesn't exist", exception);
        return badRequest().body("sql injection");
    }
}
