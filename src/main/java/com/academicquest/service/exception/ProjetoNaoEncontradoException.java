package com.academicquest.service.exception;

public class ProjetoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProjetoNaoEncontradoException(String msg) {
        super(msg);
    }

}
