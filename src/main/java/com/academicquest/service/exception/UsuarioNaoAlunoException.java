package com.academicquest.service.exception;

public class UsuarioNaoAlunoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UsuarioNaoAlunoException(String msg) {
        super(msg);
    }

}
