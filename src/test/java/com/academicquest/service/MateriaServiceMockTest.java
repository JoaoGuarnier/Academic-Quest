package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	private List<Materia> a;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999;
		materia = createMateria();
		a = new ArrayList<>(List.of(materia));

		Mockito.doReturn(List.of()).when(materiaRepository).findAll();
		Mockito.when(materiaRepository.findAll()).thenReturn(a);

		Mockito.doReturn(List.of(materia)).when(materiaRepository).findByTurmaId(existingId);
		Mockito.doReturn(List.of()).when(materiaRepository).findByTurmaId(nonExistingId);

		Mockito.when(materiaRepository.save(ArgumentMatchers.any())).thenReturn(materia);
	}
	
	@Test
	public void getNotByTurmaId() {

		List<MateriaDTO> materiaDto = materiaService.getByTurmaId(existingId);

		assertThat(materiaDto).isNotEmpty();
		Mockito.verify(materiaRepository, Mockito.times(1)).findByTurmaId(existingId);
	}

	@Test
	public void getByTurmaId() {

		List<MateriaDTO> userDto2 = materiaService.getByTurmaId(nonExistingId);

		assertThat(userDto2).isNullOrEmpty();
		Mockito.verify(materiaRepository, Mockito.times(1)).findByTurmaId(nonExistingId);
	}
	
	@Test
	public void getNotAll() {
		
		List<MateriaDTO> materiaDto = materiaService.getAll();
		
		assertThat(materiaDto).isNotEmpty();
		
		Mockito.verify(materiaRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void getAll() {

		List<MateriaDTO> userDto2 = materiaService.getAll();
		userDto2.clear();
	
		assertThat(userDto2).isNullOrEmpty();
		Mockito.verify(materiaRepository, Mockito.times(1)).findAll();
	}
}
