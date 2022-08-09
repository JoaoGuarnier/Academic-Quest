package com.academicquest.repository;

import com.academicquest.model.TarefaGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface TarefaGrupoRepository extends JpaRepository<TarefaGrupo, Long> {

    List<TarefaGrupo> findByTarefaId(Long id);

    @Modifying
    @Query(value = "update tb_tarefa_grupo set status_tarefa_grupo = 'NAO_ENTREGUE' where status_tarefa_grupo = 'PENDENTE' and \n" +
            "tarefa_id in (select id from tb_tarefa where data_entrega = :data)", nativeQuery = true)
    void alterarStatusTarefasNaoEntregue(@Param("data")LocalDate data);


    @Query(value = "SELECT tbm.NOME as NOME_MATERIA, tbt.NOME, tbtg.ID, tbt.DATA_ENTREGA, tbp.NOME as NOME_PROJETO FROM TB_TAREFA_GRUPO tbtg " +
            "INNER JOIN TB_TAREFA tbt ON tbt.ID = tbtg.TAREFA_ID " +
            "INNER JOIN TB_PROJETO tbp on tbp.ID = tbt.PROJETO_ID " +
            "INNER JOIN TB_MATERIA tbm ON tbm.ID = tbp.MATERIA_ID " +
            "WHERE tbtg.GRUPO_ID = :idGrupo " +
            "AND tbtg.STATUS_TAREFA_GRUPO = 'PENDENTE'", nativeQuery = true)
    List<Tuple> buscarTarefasPendenteGrupoPorAlunoId(@Param("idGrupo") Long idGrupo);

    @Query(value = "SELECT tbm.NOME as NOME_MATERIA, tbt.NOME, tbtg.ID as ID_TAREFA_GRUPO, tbt.DATA_ENTREGA, tbp.NOME as NOME_PROJETO, tbtg.STATUS_TAREFA_GRUPO FROM TB_TAREFA_GRUPO tbtg " +
            "INNER JOIN TB_TAREFA tbt ON tbt.ID = tbtg.TAREFA_ID " +
            "INNER JOIN TB_PROJETO tbp on tbp.ID = tbt.PROJETO_ID " +
            "INNER JOIN TB_MATERIA tbm ON tbm.ID = tbp.MATERIA_ID " +
            "WHERE tbtg.GRUPO_ID = :grupoId " +
            "AND tbp.ID = :projetoId", nativeQuery = true)
    List<Tuple> buscarTarefasGrupoPorProjeto(@Param("grupoId") Long grupoId, @Param("projetoId") Long projetoId);



}
