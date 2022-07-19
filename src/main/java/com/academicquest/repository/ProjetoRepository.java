package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academicquest.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByMateriaId(Long materiaId);

    @Query(value = "SELECT AVG(nota) FROM TB_TAREFA_GRUPO WHERE TAREFA_ID in (SELECT id FROM TB_TAREFA WHERE PROJETO_ID = :projetoId) AND GRUPO_ID = :grupoId", nativeQuery = true)
    Double calcularMediaProjetoGrupo(@Param("projetoId") Long projetoId, @Param("grupoId") Long grupoId);

}
