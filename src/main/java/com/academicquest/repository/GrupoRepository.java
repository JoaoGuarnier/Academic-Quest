package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Grupo>findByMateriaId(Long id);

}
