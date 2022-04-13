package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long>{
	
	List<Materia> findByTurmaId(Long id);

}
