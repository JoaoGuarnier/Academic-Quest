package com.academicquest.controller.exceptions;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.academicquest.service.exception.AlunoLiderNaoEncontradoException;
import com.academicquest.service.exception.AlunoNaoEncontradoException;
import com.academicquest.service.exception.BadRequestException;
import com.academicquest.service.exception.ErroAoCriarRegistrosProjetoGrupoException;
import com.academicquest.service.exception.GrupoNaoEncontradoException;
import com.academicquest.service.exception.MateriaNaoEncontradaException;
import com.academicquest.service.exception.NenhumGrupoCadastradoNaMateriaException;
import com.academicquest.service.exception.ProjetoJaConcluidoException;
import com.academicquest.service.exception.ProjetoNaoEncontradoException;
import com.academicquest.service.exception.TarefaGrupoNaoEncontradoException;
import com.academicquest.service.exception.TarefaNaoEncontradaException;
import com.academicquest.service.exception.UsuarioNaoAlunoException;
import com.academicquest.service.exception.UsuarioNaoEncontradoException;

@ControllerAdvice
public class ControllerExceptionHandler{

    @ExceptionHandler(GrupoNaoEncontradoException.class)
    public ResponseEntity<StandardError> grupoNaoEncontrado(GrupoNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Grupo não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<StandardError> alunoNaoEncontrado(AlunoNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Aluno não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(AlunoLiderNaoEncontradoException.class)
    public ResponseEntity<StandardError> alunoLiderNaoEncontrado(AlunoLiderNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Aluno líder não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(MateriaNaoEncontradaException.class)
    public ResponseEntity<StandardError> materiaNaoEncontrada(MateriaNaoEncontradaException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Matéria não encontrada");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(ErroAoCriarRegistrosProjetoGrupoException.class)
    public ResponseEntity<StandardError> erroAoCriarRegistrosProjetoGrupo(ErroAoCriarRegistrosProjetoGrupoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Erro ao executar ação");
        standardError.setMessage("Erro ao gerar registros do projeto grupo");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
    }

    @ExceptionHandler(ProjetoNaoEncontradoException.class)
    public ResponseEntity<StandardError> projetoNaoEncontrado(ProjetoNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Projeto não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<StandardError> tarefaNaoEncontrada(TarefaNaoEncontradaException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Tarefa não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(TarefaGrupoNaoEncontradoException.class)
    public ResponseEntity<StandardError> tarefaGrupoNaoEncontrado(TarefaGrupoNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("Tarefa Grupo não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
    
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<StandardError> handleBadRequestException(BadRequestException e, HttpServletRequest req) {

    	StandardError me = StandardError.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .error("Valor do atributo vazio")
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(me);
    }

    @ExceptionHandler(UsuarioNaoAlunoException.class)
    public ResponseEntity<StandardError> usuarioNaoAluno(UsuarioNaoAlunoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Erro de perfil");
        standardError.setMessage("O usuário não é um aluno");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<StandardError> usuarioNaoEncontrado(UsuarioNaoEncontradoException exception, HttpServletRequest request) {
    	StandardError standardError = new StandardError();
    	standardError.setTimestamp(Instant.now());
    	standardError.setStatus(HttpStatus.NOT_FOUND.value());
    	standardError.setError("Erro de perfil");
    	standardError.setMessage("O usuário não encontrado");
    	standardError.setPath(request.getRequestURI());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    
    @ExceptionHandler(ProjetoJaConcluidoException.class)
    public ResponseEntity<StandardError> projetoJaConcluidoException(ProjetoJaConcluidoException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Erro ao avaliar projeto");
        standardError.setMessage("O projeto já esta concluído");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(NenhumGrupoCadastradoNaMateriaException.class)
    public ResponseEntity<StandardError> nenhumGrupoCadastradoNaMateriaException(NenhumGrupoCadastradoNaMateriaException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Erro ao criar o projeto");
        standardError.setMessage("Não existem grupos cadastrados para a materia do projeto");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage("id não encontrado");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

}
