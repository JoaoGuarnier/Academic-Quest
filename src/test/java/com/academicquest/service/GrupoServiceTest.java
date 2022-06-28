package com.academicquest.service;

import static com.academicquest.components.UtilMock.Grupo_ID;
import static com.academicquest.components.UtilMock.Grupo_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoPostDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static com.academicquest.mockDados.MockDadosTest.createGrupo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

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
import com.academicquest.service.exception.BadRequestException;

@SpringBootTest
@Transactional
public class GrupoServiceTest {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	@Test
	@DisplayName("Deve salvar um GrupoService.")
	public void saveGrupoServiceExistente() {

		GrupoPostDTO grupoDto = createGrupoPostDTO();
		Grupo grupo = grupoRepository.save(createGrupo());

		grupoService.save(grupoDto);

		assertEquals(grupoDto.getNome(),         grupo.getNome());
		assertEquals(grupoDto.getMateriaId(),    grupo.getMateria().getId());
		assertEquals(grupoDto.getAlunoLiderId(), grupo.getAlunoLider().getId());
		
		assertEquals(grupoDto.getAlunosId(), Arrays.asList(grupo.getAlunos().get(0).getId()));
	}

	@Test
	@DisplayName("Se a lista de Grupo tiver elemento retorna um true, e se o id existe no banco")
	public void getGrupo() {

		List<GrupoMateriaDTO> grupoDto = grupoService.getByMateriaId(Grupo_ID);

		assertThat(grupoDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista Grupo estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotGrupo() {

		Executable executable = () -> grupoService.getByMateriaId(Grupo_ID_NAO_EXISTE);
		
		Exception expectedEx = assertThrows(BadRequestException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado buscarAlunosSemGrupo"); 
	}

	@Test
	@DisplayName("Deve retorna um grupo se o id, existe no banco")
	public void getGrupoId() {

		GrupoDTO grupoDto = grupoService.getById(Grupo_ID);

		assertThat(grupoDto).isNotNull();
	}
	
	@Test
	@DisplayName("Deve lanca uma exception quando o valor nao existir no banco")
	public void getNotGrupoId() {
		
		Executable executable = () -> grupoService.getById(Grupo_ID_NAO_EXISTE);
		
		Exception expectedEx = assertThrows(BadRequestException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
	}

	@Test
	@DisplayName("Se a lista Alunos Sem Grupo tiver elemento retorna um true, e se o id existe no banco")
	public void getAlunosSemGrupo() {

		List<UserDTO> grupoDto = grupoService.buscarAlunosSemGrupo(Grupo_ID);

		assertThat(grupoDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista Alunos Sem Grupo estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotAlunosSemGrupo() {
		
		Executable executable = () -> grupoService.buscarAlunosSemGrupo(Grupo_ID_NAO_EXISTE);
		
		Exception expectedEx = assertThrows(BadRequestException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado buscarAlunosSemGrupo"); 
	}

	@Test
	@DisplayName("Deve alterar um GrupoUpdateDto por id.")
	public void updateGrupo() {

		GrupoUpdateDTO grupoDto = createGrupoUpdateDTO();

		Grupo grupo = grupoRepository.save(createGrupo());

		grupoService.updateGrupo(grupoDto, Grupo_ID);

		assertEquals(grupoDto.getNome(),         grupo.getNome());
		assertEquals(grupoDto.getIdAlunoLider(), grupo.getAlunoLider().getId());
		
		assertEquals(grupoDto.getIdAlunos(), Arrays.asList(grupo.getAlunos().get(0).getId()));
	}
}
