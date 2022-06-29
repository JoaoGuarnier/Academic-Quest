package com.academicquest.repository;

import com.academicquest.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query(value = "select id from tb_tarefa where projeto_id = :id", nativeQuery = true)
    List<Long> buscarIdsTarefasPorProjetoId(@Param("id") Long id);

}
