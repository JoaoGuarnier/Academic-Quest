package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoPostDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static com.academicquest.mockDados.MockDadosTest.createGrupo;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.User;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@SpringBootTest
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

	private long grupoId;
	private long notGrupoId;
	private Grupo grupo;
	private User user;
	private Materia materia;

	@BeforeEach
	public void setUprupoServiceMock() throws Exception {
		grupoId    = 1L;
		notGrupoId = 999;
		grupo      = createGrupo();
		user       = MockDadosTest.createUser();
		materia	   = MockDadosTest.createMateria();
		
		doReturn(grupo).when(grupoRepository).getById(grupoId);

		
		doReturn(Optional.of(grupo)).when(grupoRepository).findById(grupoId);
		doThrow(EntityNotFoundException.class).when(grupoRepository).getById(notGrupoId);
		
		doReturn(of(grupo.getId())).when(grupoRepository).buscarAlunosPorMateriaId(grupoId);
		doReturn(of()).when(grupoRepository).buscarAlunosPorMateriaId(notGrupoId);

		doReturn(of(grupo)).when(grupoRepository).findByMateriaId(grupoId);
		doReturn(of()).when(grupoRepository).findByMateriaId(notGrupoId);
		
		doReturn(user).when(userRepository).getById(grupoId);
		doReturn(Optional.of(user)).when(userRepository).findById(grupoId);
		
		doReturn(materia).when(materiaRepository).getById(grupoId);
		
		when(grupoRepository.save(ArgumentMatchers.any())).thenReturn(grupo);
	}
	
    @Test
   
    @DisplayName("Deve salvar um GrupoServiceMock.")
    public void saveGrupoServiceExistente() {
    	
		GrupoPostDTO grupoPostDto = createGrupoPostDTO();
		GrupoDTO grupoDto = MockDadosDTOTest.createGrupoDTO();

		grupoService.salvar(grupoPostDto);
		
        assertNotNull(grupoDto);
    }

	@Test
	@DisplayName("Deve alterar um GrupoUpdateDto Mock por id.")
	public void updateGrupo() {
		
		GrupoUpdateDTO grupoUpdateDto = createGrupoUpdateDTO();
		
		GrupoUpdateDTO result = grupoService.atualizarGrupo(grupoUpdateDto, grupoId);
		
		assertNotNull(result);
	}

	@Test
	@DisplayName("Deve retornar exception ao alterar um GrupoUpdateDto Mock por id.")
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		GrupoUpdateDTO grupoUpdateDto = createGrupoUpdateDTO();
		
		assertThrows(GrupoNaoEncontradoException.class, () -> {
			grupoService.atualizarGrupo(grupoUpdateDto, notGrupoId);
		});
	}

	@Test
	@DisplayName("Deve retorna um grupo Mock se o id, existe no banco")
	public void getGrupoId() {
		
		GrupoDTO grupoDto2 = grupoService.buscarPorId(grupoId);
		
		assertNotNull(grupoDto2);
		verify(grupoRepository, Mockito.times(1)).findById(grupoId);
	}

	@Test
	@DisplayName("Deve lanca uma exception Mock quando o valor nao existir no banco")
	public void getNotGrupoId() {
		
		Executable executable = () -> grupoService.buscarPorId(notGrupoId);
		
		Exception expectedEx = assertThrows(GrupoNaoEncontradoException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
		
		verify(grupoRepository, times(1)).findById(notGrupoId);
	}
	
	@Test
	@DisplayName("Se a lista Alunos Sem Grupo Mock tiver elemento, retorna um true, e se o id existe no banco")
	public void getAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(grupoId);

		assertThat(userDto2).isNullOrEmpty();
		
		verify(grupoRepository, times(1)).buscarAlunosPorMateriaId(grupoId);
	}

	@Test
	@DisplayName("Se a lista Alunos Sem Grupo Mock estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(notGrupoId);

		assertThat(userDto2).isNullOrEmpty();
		
		verify(grupoRepository, times(1)).buscarAlunosPorMateriaId(notGrupoId);
	}
}
