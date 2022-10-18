package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academicquest.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long>{
	
	@Query(value = "SELECT tbm.id, tbm.NOME, tbm.PROFESSOR_ID, tbm.TURMA_ID FROM TB_MATERIA tbm \r\n"
			+ "WHERE tbm.PROFESSOR_ID  = :idUser \r\n"
			+ "AND tbm.TURMA_ID = :idProfessor", nativeQuery = true)
	List<Materia> findByTurmaId(@Param("idProfessor") Long idProfessor, @Param("idUser") Long idUser);
	
}
