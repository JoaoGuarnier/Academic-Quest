package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
	public void getAll() {
		
		List<TurmaDTO> turmaDto = turmaService.getAll();
		
		assertThat(turmaDto).isNotEmpty();
	}
	
	@Test
	public void getNotAll() {

		List<TurmaDTO> turmaDto = turmaService.getAll();
		turmaDto.clear();
	
		assertThat(turmaDto).isNullOrEmpty();
	}
}
