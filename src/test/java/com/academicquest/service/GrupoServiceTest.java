package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoPostDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
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
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Grupo;
import com.academicquest.repository.GrupoRepository;

@SpringBootTest
@Transactional
public class GrupoServiceTest {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 999l;
	}

	@Test
	public void deleteShouldDeleteResoucerWhenIdExist() {

		GrupoPostDTO grupoDto = createGrupoPostDTO();

		Grupo grupo = grupoRepository.save(MockDadosTest.createGrupo());

		grupoService.save(grupoDto);

		assertEquals(grupoDto.getNome(), grupo.getNome());
		assertEquals(grupoDto.getMateriaId(), grupo.getMateria().getId());
		assertEquals(grupoDto.getAlunoLiderId(), grupo.getAlunoLider().getId());
		assertEquals(grupoDto.getAlunosId(), Arrays.asList(grupo.getAlunos().get(0).getId()));
	}

	@Test
	public void getByMateriaId() {

		List<GrupoMateriaDTO> grupoDto2 = grupoService.getByMateriaId(existingId);

		assertThat(grupoDto2).isNotEmpty();
	}

	@Test
	public void getByNotMateriaId() {

		List<GrupoMateriaDTO> grupoDto2 = grupoService.getByMateriaId(nonExistingId);

		assertThat(grupoDto2).isNullOrEmpty();
	}

	@Test
	public void getById() {

		GrupoDTO grupoDto2 = grupoService.getById(existingId);

		assertThat(grupoDto2).isNotNull();
	}
	
	@Test
	public void getByNotMId() {
		
		Executable executable = () -> grupoService.getById(nonExistingId);
		
		Exception expectedEx = assertThrows(EntityNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Grupo não encontrado"); 
	}

	@Test
	public void buscarAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(existingId);

		assertThat(userDto2).isNotEmpty();
	}

	@Test
	public void buscarNotListAlunosSemGrupo() {

		List<UserDTO> userDto2 = grupoService.buscarAlunosSemGrupo(nonExistingId);

		assertThat(userDto2).isNullOrEmpty();
	}

	@Test
	public void updateGrupo() {

		GrupoUpdateDTO grupoDto = createGrupoUpdateDTO();

		Grupo grupo = grupoRepository.save(MockDadosTest.createGrupo());

		grupoService.updateGrupo(grupoDto, existingId);

		assertEquals(grupoDto.getNome(), grupo.getNome());
		assertEquals(grupoDto.getIdAlunoLider(), grupo.getAlunoLider().getId());
		assertEquals(grupoDto.getIdAlunos(), Arrays.asList(grupo.getAlunos().get(0).getId()));
	}
}
