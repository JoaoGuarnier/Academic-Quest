package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createProjetoPostDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Projeto;
import com.academicquest.repository.ProjetoRepository;

@SpringBootTest
@Transactional
public class ProjetoServiceTest {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private ProjetoService projetoService;

	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 999l;
	}

	@Test
	public void deleteShouldDeleteResoucerWhenIdExist() {

		ProjetoPostDTO projetoDto = createProjetoPostDTO();

		Projeto projeto = projetoRepository.save(MockDadosTest.createProjeto());

		projetoService.save(projetoDto);

		assertEquals(projetoDto.getNome(),      projeto.getNome());
		assertEquals(projetoDto.getIdMateria(), projeto.getMateria().getId());
		assertEquals(projetoDto.getDescricao(), projeto.getDescricao());
	}

	@Test
	public void getNotByTurmaId() {
		
		projetoRepository.save(MockDadosTest.createProjeto());

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(existingId);

		assertThat(projetoDto).isNotEmpty();
	}

	@Test
	public void getByTurmaId() {

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(nonExistingId);

		assertThat(projetoDto).isNullOrEmpty();
	}
	
	@Test
	public void getNotAll() {
		
		projetoRepository.save(MockDadosTest.createProjeto());
		
		List<ProjetoDTO> projetoDto = projetoService.getAll();
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Test
	public void getAll() {

		List<ProjetoDTO> projetoDto = projetoService.getAll();
		projetoDto.clear();
	
		assertThat(projetoDto).isNullOrEmpty();
	}
}
