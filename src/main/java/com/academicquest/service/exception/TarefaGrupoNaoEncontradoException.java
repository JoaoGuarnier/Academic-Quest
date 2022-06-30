package com.academicquest.service.exception;

public class TarefaGrupoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TarefaGrupoNaoEncontradoException(String msg) {
        super(msg);
    }

}
