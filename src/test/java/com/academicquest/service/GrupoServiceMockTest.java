package com.academicquest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Grupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class GrupoServiceMockTest {

	@InjectMocks
	private GrupoService grupoService;

	@Mock
	private GrupoRepository grupoRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private MateriaRepository materiaRepository;

	private Long existingId;
	private long nonExistingId;
	private Grupo grupo;
	private GrupoUpdateDTO grupoUpdateDto;
	private GrupoDTO grupoDto;

	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999;
		grupo = MockDadosTest.createGrupo();
		grupoUpdateDto = MockDadosTest.createGrupoUpdateDTO();
		grupoDto = MockDadosTest.createGrupoDTO();

		Mockito.doReturn(List.of(grupo)).when(grupoRepository).buscaAlunosComGrupoMateria(existingId);
		Mockito.doReturn(List.of()).when(grupoRepository).buscaAlunosComGrupoMateria(nonExistingId);

		Mockito.doReturn(List.of(grupo)).when(grupoRepository).findByMateriaId(existingId);
		Mockito.doReturn(List.of()).when(grupoRepository).findByMateriaId(nonExistingId);

		Mockito.when(grupoRepository.save(ArgumentMatchers.any())).thenReturn(grupo);
		
		Mockito.doReturn(grupo).when(grupoRepository).getById(existingId);
		Mockito.doThrow(EntityNotFoundException.class).when(grupoRepository).getById(nonExistingId);
	}

	@Test
	public void updateShouldReturnDtoWhenIdExists() {
		GrupoUpdateDTO grupoUpdateDto = MockDadosTest.createGrupoUpdateDTO();
		GrupoUpdateDTO result = grupoService.updateGrupo(grupoUpdateDto, existingId);
		assertNotNull(result);
	}

//	@Test
//	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
//		assertThrows(ResourceNotFoundException.class, () -> {
//			grupoService.update(grupoUpdateDto, nonExistingId);
//		});
//	}

	@Ignore
	public void findByIdShouldReturnDtoWhenIdExists() {
		
		GrupoDTO grupoDto2 = grupoService.getById(existingId);
		
		assertNotNull(grupoDto2);
		Mockito.verify(grupoRepository, Mockito.times(1)).getById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Executable executable = () -> grupoService.getById(nonExistingId);
		
		Exception expectedEx = assertThrows(EntityNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
		
		Mockito.verify(grupoRepository, Mockito.times(1)).findById(nonExistingId);
	}
}
