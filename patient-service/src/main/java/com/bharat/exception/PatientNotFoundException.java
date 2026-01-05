package com.bharat.exception;

public class PatientNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5216832181297989093L;

	public PatientNotFoundException(String message) {
		super(message);
	}

}
