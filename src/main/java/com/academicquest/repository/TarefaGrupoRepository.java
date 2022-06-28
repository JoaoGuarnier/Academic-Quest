package com.academicquest.repository;

import com.academicquest.model.TarefaGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaGrupoRepository extends JpaRepository<TarefaGrupo, Long> {

    List<TarefaGrupo> findByTarefaId(Long id);

}
