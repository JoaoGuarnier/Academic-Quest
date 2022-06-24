package com.academicquest.service;

import static com.academicquest.components.UtilMock.PROJETO_ID;
import static com.academicquest.components.UtilMock.PROJETO_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosDTOTest.createProjetoPostDTO;
import static com.academicquest.mockDados.MockDadosTest.createProjeto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.model.Projeto;
import com.academicquest.repository.ProjetoRepository;

@SpringBootTest
@Transactional
public class ProjetoServiceTest {

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private ProjetoService projetoService;

	@Test
	@DisplayName("Deve salvar um ProjetoService")
	public void saveProjeto() {

		ProjetoPostDTO projetoDto = createProjetoPostDTO();

		Projeto projeto = projetoRepository.save(createProjeto());

		projetoService.save(projetoDto);

		assertEquals(projetoDto.getNome(),      projeto.getNome());
		assertEquals(projetoDto.getDescricao(), projeto.getDescricao());
		assertEquals(projetoDto.getIdMateria(), projeto.getMateria().getId());
	}

	@Test
	@DisplayName("Se a lista de todas as Materia tiver elemento retorna um true, e se o id existe no banco")
	public void getMateriaId() {
		
		projetoRepository.save(createProjeto());

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(PROJETO_ID);

		assertThat(projetoDto).isNotEmpty();
	}

	@Test
	@DisplayName("Se a lista de todas as Materia estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotMateriaId() {

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(PROJETO_ID_NAO_EXISTE);

		assertThat(projetoDto).isNullOrEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todos os Projeto tiver elemento retorna um true")
	public void getProjetoAll() {
		
		projetoRepository.save(createProjeto());
		
		List<ProjetoDTO> projetoDto = projetoService.getAll();
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Se a lista de todos os Projeto estiver vazia ou nula deve retorna um False")
	public void getNotProjetoAll() {

		List<ProjetoDTO> projetoDto = projetoService.getAll();
		projetoDto.clear();
	
		assertThat(projetoDto).isNullOrEmpty();
	}
}
