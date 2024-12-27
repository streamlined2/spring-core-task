package com.streamlined.tasks.exception;

public class EntityQueryException extends RuntimeException {

	public EntityQueryException(String message) {
		super(message);
	}

	public EntityQueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
