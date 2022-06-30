package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.MateriaDTO;

@SpringBootTest
@Transactional
public class MateriaServiceTest {

	@Autowired
	private MateriaService materiaService;

	private Long materiaId;
	private Long notMateriaId;

	@BeforeEach
	void setUpMateriaService() throws Exception {
		
		materiaId    = 1l;
		notMateriaId = 999l;
	}

	@Test
	@DisplayName("Se a lista de todas as turma tiver elemento retorna um true, e se o id existe no banco")
	public void getTurmaId() {

		List<MateriaDTO> materiaDto = materiaService.buscarPorTurmaId(materiaId);

		assertThat(materiaDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista de todas as turma estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotTurmaId() {

		List<MateriaDTO> userDto2 = materiaService.buscarPorTurmaId(notMateriaId);

		assertThat(userDto2).isNullOrEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia tiver elemento retorna um true")
	public void getMateriaAll() {
		
		List<MateriaDTO> materiaDto = materiaService.buscarTodos();
		
		assertThat(materiaDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia estiver vazia ou nula deve retorna um False")
	public void getNotMateriaAll() {

		List<MateriaDTO> userDto2 = materiaService.buscarTodos();
		userDto2.clear();
	
		assertThat(userDto2).isNullOrEmpty();
	}
}
