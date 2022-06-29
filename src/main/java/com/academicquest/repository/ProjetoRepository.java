package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByMateriaId(Long materiaId);
}
