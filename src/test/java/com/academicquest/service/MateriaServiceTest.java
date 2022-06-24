package com.academicquest.service;

import static com.academicquest.components.UtilMock.Materia_ID;
import static com.academicquest.components.UtilMock.Materia_ID_NAO_EXISTE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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


	@Test
	@DisplayName("Se a lista de todas as turma tiver elemento retorna um true, e se o id existe no banco")
	public void getTurmaId() {

		List<MateriaDTO> materiaDto = materiaService.getByTurmaId(Materia_ID);

		assertThat(materiaDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista de todas as turma estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotTurmaId() {

		List<MateriaDTO> userDto2 = materiaService.getByTurmaId(Materia_ID_NAO_EXISTE);

		assertThat(userDto2).isNullOrEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia tiver elemento retorna um true")
	public void getMateriaAll() {
		
		List<MateriaDTO> materiaDto = materiaService.getAll();
		
		assertThat(materiaDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia estiver vazia ou nula deve retorna um False")
	public void getNotMateriaAll() {

		List<MateriaDTO> userDto2 = materiaService.getAll();
		userDto2.clear();
	
		assertThat(userDto2).isNullOrEmpty();
	}
}
