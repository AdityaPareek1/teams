package com.findit.teams.web.rest.errors;

public class AuthenticationFailException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public AuthenticationFailException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
