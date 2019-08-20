package com.patrick.authserver.auth.beans;

public class RegistrationResponse {

	private String message;

	public RegistrationResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
