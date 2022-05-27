package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 999l;
	}

	@Test
	public void getNotByTurmaId() {

		List<MateriaDTO> materiaDto = materiaService.getByTurmaId(existingId);

		assertThat(materiaDto).isNotEmpty();
	}

	@Test
	public void getByTurmaId() {

		List<MateriaDTO> userDto2 = materiaService.getByTurmaId(nonExistingId);

		assertThat(userDto2).isNullOrEmpty();
	}
	
	@Test
	public void getNotAll() {
		
		List<MateriaDTO> materiaDto = materiaService.getAll();
		
		assertThat(materiaDto).isNotEmpty();
	}
	
	@Test
	public void getAll() {

		List<MateriaDTO> userDto2 = materiaService.getAll();
		userDto2.clear();
	
		assertThat(userDto2).isNullOrEmpty();
	}
}
