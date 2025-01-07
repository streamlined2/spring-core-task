package com.streamlined.tasks.exception;

public class InvalidEntityDataException extends RuntimeException {

    public InvalidEntityDataException(String message) {
        super(message);
    }

    public InvalidEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
