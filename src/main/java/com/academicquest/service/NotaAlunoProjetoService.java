package com.academicquest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.model.NotaAlunoProjeto;
import com.academicquest.model.Projeto;
import com.academicquest.model.User;
import com.academicquest.repository.NotaAlunoProjetoRepository;
import com.academicquest.service.exception.UsuarioNaoAlunoException;

@Service
public class NotaAlunoProjetoService {

    @Autowired
    private NotaAlunoProjetoRepository notaAlunoProjetoRepository;

    public void darNotaAlunoProjeto(Projeto projeto, User user) {

        if (user.getIsAluno() == 0) {
            throw new UsuarioNaoAlunoException("O usuário não é um aluno");
        }
        NotaAlunoProjeto notaAlunoProjeto = new NotaAlunoProjeto();
        notaAlunoProjeto.setAluno(user);
        notaAlunoProjeto.setProjeto(projeto);
        notaAlunoProjeto.setNota(10.0);

        notaAlunoProjetoRepository.save(notaAlunoProjeto);
    }
}
