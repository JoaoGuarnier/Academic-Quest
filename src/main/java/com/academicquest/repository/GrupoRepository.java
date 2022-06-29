package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academicquest.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Grupo>findByMateriaId(Long id);
	
	@Query(value = "SELECT (tbu.id) FROM TB_USER tbu\r\n"
			+ "INNER JOIN TB_MATERIA tbm\r\n"
			+ "ON tbu.TURMA_ID = tbm.TURMA_ID\r\n"
			+ "WHERE tbm.ID = :id"
			,nativeQuery = true)
	List<Long> buscarAlunosPorMateriaId(@Param("id") Long materiaId);
	
	@Query(value = "SELECT (tbgu.user_id) FROM TB_GRUPO tbg\r\n"
			+ "INNER JOIN TB_GRUPO_USER tbgu\r\n"
			+ "ON tbg.ID = tbgu.GRUPO_ID\r\n"
			+ "INNER JOIN TB_MATERIA tbm\r\n"
			+ "ON tbm.ID = tbg.MATERIA_ID\r\n"
			+ "WHERE tbm.ID = :id", nativeQuery = true)
	List<Long> buscaAlunosComGrupoPorMateriaId(@Param("id") Long materiaId);

	@Query(value = "select id from tb_grupo where materia_id = :id", nativeQuery = true)
	List<Long> buscaGruposPorMateriaId(@Param("id") Long materiaId);
	
	

}
