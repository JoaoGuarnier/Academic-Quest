package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academicquest.model.Grupo;

import javax.persistence.Tuple;

public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	List<Grupo>findByMateriaId(Long id);
	
	@Query(value = "SELECT (tbu.id) FROM tb_user tbu "
			+ "INNER JOIN tb_materia tbm "
			+ "ON tbu.TURMA_ID = tbm.TURMA_ID "
			+ "WHERE tbm.ID = :id"
			,nativeQuery = true)
	List<Long> buscarAlunosPorMateriaId(@Param("id") Long materiaId);
	
	@Query(value = "SELECT (tbgu.user_id) FROM tb_grupo tbg "
			+ "INNER JOIN tb_grupo_user tbgu "
			+ "ON tbg.ID = tbgu.GRUPO_ID "
			+ "INNER JOIN tb_materia tbm "
			+ "ON tbm.ID = tbg.MATERIA_ID "
			+ "WHERE tbm.ID = :id", nativeQuery = true)
	List<Long> buscaAlunosComGrupoPorMateriaId(@Param("id") Long materiaId);

	@Query(value = "select id from tb_grupo where materia_id = :id", nativeQuery = true)
	List<Long> buscaGruposPorMateriaId(@Param("id") Long materiaId);

	@Query(value = "SELECT tbg.ID, tbg.NOME , tbg.USER_LIDER_ID FROM tb_grupo_user tbgu " +
			"INNER JOIN tb_grupo tbg ON tbg.ID = tbgu.GRUPO_ID " +
			"WHERE tbgu.USER_ID = :id", nativeQuery = true)
	List<Tuple> buscarGrupoDoAlunoPorAlunoId(@Param("id") Long id);

}
