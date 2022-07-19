package com.academicquest.service.exception;

public class GrupoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String msg) {
        super(msg);
    }

}
