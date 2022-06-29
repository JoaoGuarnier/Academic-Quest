package com.academicquest.repository;

import com.academicquest.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByMateriaId(Long materiaId);

}
