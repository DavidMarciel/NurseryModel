package com.nursery.exceptions;

public class FormatSizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FormatSizeException() {
		super("The input coordenates are too big, please use a valid ones");
	}

}
