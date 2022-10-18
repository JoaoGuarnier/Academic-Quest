package com.academicquest.service;

import com.academicquest.dto.notificacao.NotificacaoDTO;
import com.academicquest.enums.TIPO_NOTIFICACAO;
import com.academicquest.model.*;
import com.academicquest.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;


    public void marcarComoVisto(Long notificacaoId) {
        Optional<Notificacao> optionalNotificacao = notificacaoRepository.findById(notificacaoId);
        Notificacao notificacao = optionalNotificacao.orElseThrow(() -> new EntityNotFoundException());
        notificacao.setFlagVisto(true);
        notificacaoRepository.save(notificacao);
    }

    public List<NotificacaoDTO> buscarNotificacoesUser(Long idUser) {
        List<Notificacao> notificacoes = notificacaoRepository.buscarNotificacaoPorUserNotificado(idUser);
        List<NotificacaoDTO> notificacaoDTOList = notificacoes.stream().map(NotificacaoDTO::new).collect(Collectors.toList());
        return notificacaoDTOList;
    }

    public void notificar(Projeto projeto) {
        Long turmaId = projeto.getMateria().getTurma().getId();
        Long professorId = projeto.getMateria().getProfessor().getId();
        List<Tuple> alunosTurma = notificacaoRepository.buscarAlunosPorTurmaId(turmaId);
        String mensagem = String.format("O projeto %s da materia %s foi criado!", projeto.getNome(), projeto.getMateria().getNome());
        alunosTurma.stream().forEach(aluno -> {
            Long userId = Long.valueOf(aluno.get(0).toString());

            Notificacao notificacao = Notificacao.builder().
                    idUserCriadorNotificacao(professorId)
                    .userNotificado(userId)
                    .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_NOVO_PROJETO)
                    .mensagem(mensagem)
                    .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .flagVisto(false)
                    .build();
            notificacaoRepository.save(notificacao);
        });
    }

    public void notificar(Tarefa tarefa) {
        Projeto projeto = tarefa.getProjeto();
        Long turmaId = projeto.getMateria().getTurma().getId();
        Long professorId = projeto.getMateria().getProfessor().getId();
        List<Tuple> alunosTurma = notificacaoRepository.buscarAlunosPorTurmaId(turmaId);
        String mensagem = String.format("A atividade %s do projeto %s foi criada na matéria de %s!", tarefa.getNome(),projeto.getNome(), projeto.getMateria().getNome());

        alunosTurma.stream().forEach(aluno -> {
            Long userId = Long.valueOf(aluno.get(0).toString());

            Notificacao notificacao = Notificacao.builder().
                    idUserCriadorNotificacao(professorId)
                    .userNotificado(userId)
                    .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_NOVA_ATIVIDADE)
                    .mensagem(mensagem)
                    .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .flagVisto(false)
                    .build();
            notificacaoRepository.save(notificacao);
        });
    }

    public void notificar(TarefaGrupo tarefaGrupo) {
        Long professorId = tarefaGrupo.getTarefa().getProjeto().getMateria().getProfessor().getId();
        String primeiroNomeProfessor = tarefaGrupo.getTarefa().getProjeto().getMateria().getProfessor().getFirstName();
        String ultimoNomeProfessor = tarefaGrupo.getTarefa().getProjeto().getMateria().getProfessor().getLastName();
        String nomeProfessor = primeiroNomeProfessor + " " + ultimoNomeProfessor;
        String nomeAtividade = tarefaGrupo.getTarefa().getNome();
        String mensagem = String.format("O professor %s atribuiu uma nota para atividade %s", nomeProfessor, nomeAtividade);

        tarefaGrupo.getGrupo().getListaAlunos().stream().forEach(aluno -> {

            Long userId = aluno.getId();

            Notificacao notificacao = Notificacao.builder().
                    idUserCriadorNotificacao(professorId)
                    .userNotificado(userId)
                    .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_ATIVIDADE_GRUPO_CORRIGIDA)
                    .mensagem(mensagem)
                    .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .flagVisto(false)
                    .build();
            notificacaoRepository.save(notificacao);
        });
    }

    public void notificar(Chat chat) {
        String mensagem = String.format("%s %s enviou uma mensagem na tarefa %s", chat.getUser().getFirstName(), chat.getUser().getLastName(), chat.getTarefaGrupo().getTarefa().getNome());
        Long idUsuarioRemetente = chat.getUser().getId();
        Long idProfessorMateria = chat.getTarefaGrupo().getTarefa().getProjeto().getMateria().getProfessor().getId();
        List<Tuple> alunosParaNotificar = notificacaoRepository.buscarAlunosDoChatPorTarefaGrupoId(chat.getTarefaGrupo().getId());

        alunosParaNotificar.stream().forEach(aluno -> {
            Long userId = Long.valueOf(aluno.get(0).toString());
            if (idUsuarioRemetente != userId) {
                Notificacao notificacao = Notificacao.builder().
                        idUserCriadorNotificacao(chat.getIdUser())
                        .userNotificado(userId)
                        .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_MENSAGEM)
                        .mensagem(mensagem)
                        .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                        .flagVisto(false)
                        .build();
                notificacaoRepository.save(notificacao);
            }
        });

        if (idUsuarioRemetente != idProfessorMateria) {
            Notificacao notificacaoProfessor = Notificacao.builder().
                    idUserCriadorNotificacao(chat.getIdUser())
                    .userNotificado(idProfessorMateria)
                    .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_MENSAGEM)
                    .mensagem(mensagem)
                    .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .flagVisto(false)
                    .build();
            notificacaoRepository.save(notificacaoProfessor);
        }
    }

    public void notificarProjetoFinalizado(Projeto projeto) {
        Long turmaId = projeto.getMateria().getTurma().getId();
        Long professorId = projeto.getMateria().getProfessor().getId();
        String professorFirstName = projeto.getMateria().getProfessor().getFirstName();
        String professorLastName = projeto.getMateria().getProfessor().getLastName();
        String nomeProfessor = professorFirstName + " " + professorLastName;

        String mensagem = String.format("O professor %s da materia %s finalizou o projeto %s", nomeProfessor, projeto.getMateria().getNome(), projeto.getNome());

        List<Tuple> alunosTurma = notificacaoRepository.buscarAlunosPorTurmaId(turmaId);

        alunosTurma.stream().forEach(aluno -> {

            Long userId = Long.valueOf(aluno.get(0).toString());
            Notificacao notificacaoProfessor = Notificacao.builder().
                    idUserCriadorNotificacao(professorId)
                    .userNotificado(userId)
                    .tipoNotificacao(TIPO_NOTIFICACAO.NOTIFICACAO_MENSAGEM)
                    .mensagem(mensagem)
                    .dataNotificacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .flagVisto(false)
                    .build();
            notificacaoRepository.save(notificacaoProfessor);
        });

    }
}
