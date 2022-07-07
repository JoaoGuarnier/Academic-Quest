package com.academicquest.service.exception;

public class TarefaNaoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TarefaNaoEncontradaException(String msg) {
        super(msg);
    }

}
