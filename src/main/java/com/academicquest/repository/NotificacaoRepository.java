package com.academicquest.repository;

import com.academicquest.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    @Query(value = "SELECT * FROM TB_USER tbu " +
            "WHERE tbu.TURMA_ID = :turmaId " +
            "AND tbu.IS_ALUNO = 1", nativeQuery = true)
    List<Tuple> buscarAlunosPorTurmaId(@Param("turmaId") Long turmaId);

    @Query(value = "SELECT * FROM TB_NOTIFICACAO WHERE USER_NOTIFICADO = :userNotificado " +
            "AND FLAG_VISTO = 'FALSE'", nativeQuery = true)
    List<Notificacao> buscarNotificacaoPorUserNotificado(@Param("userNotificado") Long userNotificado);

    @Query(value = "SELECT tbu.id  FROM TB_TAREFA_GRUPO tbtg " +
            "INNER JOIN TB_GRUPO_USER tbgu ON tbtg.GRUPO_ID = tbgu.GRUPO_ID " +
            "INNER JOIN TB_USER tbu ON tbu.id = tbgu.user_id " +
            "WHERE tbtg.id = :tarefaGrupoId", nativeQuery = true)
    List<Tuple> buscarAlunosDoChatPorTarefaGrupoId(@Param("tarefaGrupoId") Long tarefaGrupoId);

}
