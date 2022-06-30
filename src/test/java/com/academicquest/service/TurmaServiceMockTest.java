package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createTurma;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.TurmaDTO;
import com.academicquest.model.Turma;
import com.academicquest.repository.TurmaRepository;

@ExtendWith(SpringExtension.class)
public class TurmaServiceMockTest {

	@InjectMocks
	private TurmaService turmaService;
	
	@Mock
	private TurmaRepository turmaRepository;
	
	private Turma turma;
	private List<Turma> listTurma;
	
	@BeforeEach
	public void setUpTurmaServiceMock() throws Exception {
		
		turma     = createTurma();
		listTurma = new ArrayList<>(of(turma));

		doReturn(of()).when(turmaRepository).findAll();
		when(turmaRepository.findAll()).thenReturn(listTurma);
	}

	@Test
	@DisplayName("Retorna uma lista de Turma Mock se tiver elementos na lista")
	public void getTurmaAll() {
		
		List<TurmaDTO> turmaDto = turmaService.buscarTodos();
		
		assertThat(turmaDto).isNotEmpty();
		
		verify(turmaRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Retorna uma lista de Turma Mock se nao tiver elementos na lista")
	public void getNotTurmaAll() {

		List<TurmaDTO> turmaDto = turmaService.buscarTodos();
		turmaDto.clear();
	
		assertThat(turmaDto).isNullOrEmpty();
		
		verify(turmaRepository, times(1)).findAll();
	}
}
