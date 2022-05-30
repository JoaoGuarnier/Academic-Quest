package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.TurmaDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Turma;
import com.academicquest.repository.TurmaRepository;

@ExtendWith(SpringExtension.class)
public class TurmaServiceMockTest {

	@InjectMocks
	private TurmaService turmaService;
	
	@Mock
	private TurmaRepository turmaRepository;
	
	private Turma turma;
	private List<Turma> a;
	
	@BeforeEach
	public void setUp() throws Exception {
		turma = MockDadosTest.createTurma();
		a = new ArrayList<>(List.of(turma));

		Mockito.doReturn(List.of()).when(turmaRepository).findAll();
		Mockito.when(turmaRepository.findAll()).thenReturn(a);
	}

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
