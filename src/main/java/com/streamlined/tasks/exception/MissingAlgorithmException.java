package com.streamlined.tasks.exception;

public class MissingAlgorithmException extends RuntimeException {

    public MissingAlgorithmException(String message) {
        super(message);
    }

    public MissingAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

}
