package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoPostDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static com.academicquest.mockDados.MockDadosTest.createGrupo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.model.Grupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@SpringBootTest
@Transactional
public class GrupoServiceTest {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	private Long grupoId;
	private Long notGrupoId;

	@BeforeEach
	void setUpGrupoService() throws Exception {
		grupoId    = 1l;
		notGrupoId = 999l;
	}

	@Test
	@DisplayName("Deve salvar um GrupoService.")
	public void saveGrupoServiceExistente() {

		GrupoPostDTO grupoDto = createGrupoPostDTO();
		Grupo grupo = grupoRepository.save(createGrupo());

		grupoService.salvar(grupoDto);

		assertEquals(grupoDto.getNome(),         grupo.getNome());
		assertEquals(grupoDto.getMateriaId(),    grupo.getMateria().getId());
		assertEquals(grupoDto.getAlunoLiderId(), grupo.getAlunoLider().getId());
		
		assertEquals(grupoDto.getListaAlunosId(), Arrays.asList(grupo.getListaAlunos().get(0).getId()));
	}

	@Test
	@DisplayName("Se a lista de Grupo tiver elemento retorna um true, e se o id existe no banco")
	public void getGrupo() {

		List<GrupoMateriaDTO> grupoDto = grupoService.buscarPorMateriaId(grupoId);

		assertThat(grupoDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista Grupo estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotGrupo() {

		List<GrupoMateriaDTO> grupoDto = grupoService.buscarPorMateriaId(notGrupoId);

		assertThat(grupoDto).isNullOrEmpty();
	}

	@Test
	@DisplayName("Deve retorna um grupo se o id, existe no banco")
	public void getGrupoId() {

		GrupoDTO grupoDto = grupoService.buscarPorId(grupoId);

		assertThat(grupoDto).isNotNull();
	}
	
	@Test
	@DisplayName("Deve lanca uma exception quando o valor nao existir no banco")
	public void getNotGrupoId() {
		
		Executable executable = () -> grupoService.buscarPorId(notGrupoId);
		
		Exception expectedEx = assertThrows(GrupoNaoEncontradoException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
	}

	@Test
	@DisplayName("Se a lista Alunos Sem Grupo tiver elemento retorna um true, e se o id existe no banco")
	public void getAlunosSemGrupo() {

		List<UserDTO> grupoDto = grupoService.buscarAlunosSemGrupo(grupoId);

		assertThat(grupoDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista Alunos Sem Grupo estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotAlunosSemGrupo() {

		List<UserDTO> grupoDto = grupoService.buscarAlunosSemGrupo(notGrupoId);

		assertThat(grupoDto).isNullOrEmpty();
	}

	@Test
	@DisplayName("Deve alterar um GrupoUpdateDto por id.")
	public void updateGrupo() {

		GrupoUpdateDTO grupoDto = createGrupoUpdateDTO();

		Grupo grupo = grupoRepository.save(createGrupo());

		grupoService.atualizarGrupo(grupoDto, grupoId);

		assertEquals(grupoDto.getNome(),         grupo.getNome());
		assertEquals(grupoDto.getAlunoLiderId(), grupo.getAlunoLider().getId());
		
		assertEquals(grupoDto.getListaAlunosId(), Arrays.asList(grupo.getListaAlunos().get(0).getId()));
	}
}
