package com.academicquest.service.exception;

public class AlunoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AlunoNaoEncontradoException(String msg) {
        super(msg);
    }

}
