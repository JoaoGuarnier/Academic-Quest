package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoPostDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
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

	private long existingId;
	private long nonExistingId;
	private Grupo grupo;

	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999;
		grupo = MockDadosTest.createGrupo();
		
		Mockito.doReturn(grupo).when(grupoRepository).getById(existingId);
		Mockito.doReturn(Optional.of(grupo)).when(grupoRepository).findById(existingId);
		Mockito.doThrow(EntityNotFoundException.class).when(grupoRepository).getById(nonExistingId);
		
		Mockito.doReturn(List.of(grupo.getId())).when(grupoRepository).buscaAlunosMateria(existingId);
		Mockito.doReturn(List.of()).when(grupoRepository).buscaAlunosMateria(nonExistingId);

		Mockito.doReturn(List.of(grupo)).when(grupoRepository).findByMateriaId(existingId);
		Mockito.doReturn(List.of()).when(grupoRepository).findByMateriaId(nonExistingId);

		Mockito.when(grupoRepository.save(ArgumentMatchers.any())).thenReturn(grupo);
	}
	
    @Test
    public void saveShouldReturnDto() {
    	
		GrupoPostDTO grupoDto = createGrupoPostDTO();

		grupoService.save(grupoDto);
		
        assertNotNull(grupoDto);
    }

	@Test
	public void updateShouldReturnDtoWhenIdExists() {
		GrupoUpdateDTO grupoUpdateDto = createGrupoUpdateDTO();
		GrupoUpdateDTO result = grupoService.updateGrupo(grupoUpdateDto, existingId);
		assertNotNull(result);
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		GrupoUpdateDTO grupoUpdateDto = createGrupoUpdateDTO();
		assertThrows(EntityNotFoundException.class, () -> {
			grupoService.updateGrupo(grupoUpdateDto, nonExistingId);
		});
	}

	@Test
	public void findByIdShouldReturnDtoWhenIdExists() {
		
		
		GrupoDTO grupoDto2 = grupoService.getById(existingId);
		
		assertNotNull(grupoDto2);
		Mockito.verify(grupoRepository, Mockito.times(1)).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Executable executable = () -> grupoService.getById(nonExistingId);
		
		Exception expectedEx = assertThrows(EntityNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
		
		Mockito.verify(grupoRepository, Mockito.times(1)).findById(nonExistingId);
	}
	
	@Test
	public void buscarAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(existingId);

		assertThat(userDto2).isNullOrEmpty();
		
		Mockito.verify(grupoRepository, Mockito.times(1)).buscaAlunosMateria(existingId);
	}

	@Test
	public void buscarNotListAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(nonExistingId);

		assertThat(userDto2).isNullOrEmpty();
		
		Mockito.verify(grupoRepository, Mockito.times(1)).buscaAlunosMateria(nonExistingId);
	}
}
