package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academicquest.model.Projeto;

import javax.persistence.Tuple;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByMateriaId(Long materiaId);

    @Query(value = "SELECT AVG(nota) FROM TB_TAREFA_GRUPO WHERE TAREFA_ID in (SELECT id FROM TB_TAREFA WHERE PROJETO_ID = :projetoId) AND GRUPO_ID = :grupoId", nativeQuery = true)
    Double calcularMediaProjetoGrupo(@Param("projetoId") Long projetoId, @Param("grupoId") Long grupoId);

    @Query(value = "SELECT tbp.NOME, tbp.STATUS, tbpg.NOTA, tbm.NOME as NOME_MATERIA, tbpg.GRUPO_ID, tbg.NOME as NOME_GRUPO,tbp.ID as PROJETO_ID FROM TB_PROJETO_GRUPO tbpg " +
            "INNER JOIN TB_PROJETO tbp ON tbpg.PROJETO_ID = tbp.ID " +
            "INNER JOIN TB_MATERIA tbm ON tbm.ID = tbp.MATERIA_ID " +
            "INNER JOIN TB_GRUPO tbg ON tbg.ID = tbpg.GRUPO_ID " +
            "WHERE tbpg.GRUPO_ID IN (SELECT tbg.ID FROM TB_GRUPO_USER tbgu " +
            "INNER JOIN TB_GRUPO tbg ON tbg.ID = tbgu.GRUPO_ID " +
            "WHERE tbgu .USER_ID = :alunoId)", nativeQuery = true)
    List<Tuple> buscarProjetosDoGrupoPorAlunoId(@Param("alunoId") Long alunoId);

}
