package com.academicquest.repository;

import com.academicquest.model.ProjetoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjetoGrupoRepository extends JpaRepository<ProjetoGrupo, Long> {

    Optional<ProjetoGrupo> findByGrupoId(Long id);

    List<ProjetoGrupo> findByProjetoId(Long id);


}
