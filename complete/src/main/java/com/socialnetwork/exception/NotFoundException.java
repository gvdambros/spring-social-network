package com.socialnetwork.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2802284994018065744L;

	public NotFoundException(String id) {
		super("Could not find " + id);
	}
}
