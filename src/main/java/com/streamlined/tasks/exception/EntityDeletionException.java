package com.streamlined.tasks.exception;

public class EntityDeletionException extends RuntimeException {

	public EntityDeletionException(String message) {
		super(message);
	}

	public EntityDeletionException(String message, Throwable cause) {
		super(message, cause);
	}

}
