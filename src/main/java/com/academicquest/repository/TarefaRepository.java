package com.academicquest.repository;

import com.academicquest.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.persistence.Tuple;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query(value = "select id from tb_tarefa where projeto_id = :id", nativeQuery = true)
    List<Long> buscarIdsTarefasPorProjetoId(@Param("id") Long id);
    
    List<Tarefa> findByProjetoId(Long id);
    
    @Query(value = "select tbtg.id, tbtg.status_tarefa_grupo, tbg.nome from tb_tarefa_grupo tbtg "
    		+ "inner join tb_grupo tbg on tbtg.grupo_id = tbg.id "
    		+ "where tbtg.tarefa_Id = :tarefaId", nativeQuery = true)
    List<Tuple> buscarTarefaGrupoPorTarefaId(@Param("tarefaId") Long tarefaId);
}
