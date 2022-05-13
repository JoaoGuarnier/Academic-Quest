package com.academicquest.repository;

import com.academicquest.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("select c from Projeto c where c.materia.id = :materiaId")
    List<Projeto> findyByMateriaId(Long materiaId);

}
