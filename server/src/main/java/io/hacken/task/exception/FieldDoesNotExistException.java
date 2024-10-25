package io.hacken.task.exception;

public class FieldDoesNotExistException extends RuntimeException {

    public FieldDoesNotExistException(String msg) {
        super(msg);
    }
}
