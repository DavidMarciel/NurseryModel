package com.nursery.exceptions;

public class FormatValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FormatValidationException() {
		super("Invalid Input");
	}

}
