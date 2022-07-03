package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.TurmaDTO;

@SpringBootTest
@Transactional
public class TurmaServiceTest {

	@Autowired
	private TurmaService turmaService;

	@Test
	@DisplayName("Retorna uma lista de Turma Mock se tiver elementos na lista")
	public void getTurmaAll() {
		
		List<TurmaDTO> turmaDto = turmaService.buscarTodos();
		
		assertThat(turmaDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Retorna uma lista de Turma Mock se nao tiver elementos na lista")
	public void getNotTurmaAll() {

		List<TurmaDTO> turmaDto = turmaService.buscarTodos();
		turmaDto.clear();
	
		assertThat(turmaDto).isNullOrEmpty();
	}
}
