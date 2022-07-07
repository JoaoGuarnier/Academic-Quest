package com.academicquest.service.exception;

public class DataIntegrityException extends RuntimeException {
	
	private static final long serialVersionUID = 1197443516427102832L;

	public DataIntegrityException(String message) {
        super(message);
    }
}