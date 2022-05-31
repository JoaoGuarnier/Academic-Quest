package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import com.academicquest.dto.MateriaDTO;
import com.academicquest.model.Materia;
import com.academicquest.repository.MateriaRepository;

@ExtendWith(SpringExtension.class)
public class MateriaServiceMockTest {

	@InjectMocks
	private MateriaService materiaService;

	@Mock
	private MateriaRepository materiaRepository;
	
	private long existingId;
	private long nonExistingId;
	private Materia materia;
	private List<Materia> listMateria;
	
	@BeforeEach
	public void setUpMateriaServiceMock() throws Exception {
		
		existingId    = 1L;
		nonExistingId = 999;
		materia       = createMateria();
		listMateria   = new ArrayList<>(of(materia));

		doReturn(of()).when(materiaRepository).findAll();
		when(materiaRepository.findAll()).thenReturn(listMateria);

		doReturn(of(materia)).when(materiaRepository).findByTurmaId(existingId);
		doReturn(of()).when(materiaRepository).findByTurmaId(nonExistingId);

		when(materiaRepository.save(any())).thenReturn(materia);
	}
	
	@Test
	@DisplayName("Se a lista de todas as turma Mock tiver elemento retorna um true, e se o id existe no banco")
	public void getTurmaId() {

		List<MateriaDTO> materiaDto = materiaService.getByTurmaId(existingId);

		assertThat(materiaDto).isNotEmpty();
		verify(materiaRepository, times(1)).findByTurmaId(existingId);
	}

	@Test
	@DisplayName("Se a lista de todas as turma Mock estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotTurmaId() {

		List<MateriaDTO> userDto2 = materiaService.getByTurmaId(nonExistingId);

		assertThat(userDto2).isNullOrEmpty();
		verify(materiaRepository, times(1)).findByTurmaId(nonExistingId);
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia Mock tiver elemento retorna um true, e se o id existe no banco")
	public void getMateriaAll() {
		
		List<MateriaDTO> materiaDto = materiaService.getAll();
		
		assertThat(materiaDto).isNotEmpty();
		
		verify(materiaRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Se a lista de todas as Materia Mock estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotMateriaAll() {

		List<MateriaDTO> userDto2 = materiaService.getAll();
		userDto2.clear();
	
		assertThat(userDto2).isNullOrEmpty();
		
		verify(materiaRepository, times(1)).findAll();
	}
}
