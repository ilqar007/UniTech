package com.uni.tech.exception;

public class AccountDoesNotExistsException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1180822918717228267L;

	public AccountDoesNotExistsException(String message) {
		super(message);
	}

}
