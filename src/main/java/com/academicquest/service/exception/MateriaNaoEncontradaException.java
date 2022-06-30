package com.academicquest.service.exception;

public class MateriaNaoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MateriaNaoEncontradaException(String msg) {
        super(msg);
    }

}
