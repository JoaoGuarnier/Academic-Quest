package com.academicquest.repository;

import com.academicquest.model.TarefaGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TarefaGrupoRepository extends JpaRepository<TarefaGrupo, Long> {

    List<TarefaGrupo> findByTarefaId(Long id);

    @Modifying
    @Query(value = "update tb_tarefa_grupo set status_tarefa_grupo = 'NAO_ENTREGUE' where status_tarefa_grupo = 'PENDENTE' and \n" +
            "tarefa_id in (select id from tb_tarefa where data_entrega = :data)", nativeQuery = true)
    void alterarStatusTarefasNaoEntregue(@Param("data")LocalDate data);



}
