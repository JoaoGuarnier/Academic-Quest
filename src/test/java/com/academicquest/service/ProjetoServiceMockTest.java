package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createProjetoPostDTO;
import static com.academicquest.mockDados.MockDadosTest.createProjeto;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.model.Projeto;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.ProjetoRepository;

@ExtendWith(SpringExtension.class)
public class ProjetoServiceMockTest {

	@InjectMocks
	private ProjetoService projetoService;
	
	@Mock
	private ProjetoRepository projetoRepository;
	
	@Mock
    private MateriaRepository materiaRepository;
	
	private long projetoId;
	private long notProjetoId;
	private Projeto projeto;
	private List<Projeto> listPorjeto;
	
	@BeforeEach
	public void setUpProjetoServiceMock() throws Exception {
		
		projetoId    = 1L;
		notProjetoId = 999;
		projeto      = createProjeto();
		listPorjeto  = new ArrayList<>(of(projeto));

		doReturn(of()).when(projetoRepository).findAll();
		when(projetoRepository.findAll()).thenReturn(listPorjeto);

		doReturn(of(projeto)).when(projetoRepository).findByMateriaId(projetoId);
		doReturn(of()).when(projetoRepository).findByMateriaId(notProjetoId);
		
		Mockito.when(projetoRepository.save(ArgumentMatchers.any())).thenReturn(projeto);
	}

	@Ignore
	@DisplayName("Deve salvar um ProjetoServiceMock")
	public void saveProjeto() {

		ProjetoPostDTO projetoDto = createProjetoPostDTO();

		Projeto projeto = projetoRepository.save(createProjeto());
		projetoService.save(createProjetoPostDTO());
//		service.criaRegistrosProjetoGrupo(projetoDto, projeto);
		assertEquals(projetoDto.getNome(),      projeto.getNome());
		assertEquals(projetoDto.getIdMateria(), projeto.getMateria().getId());
		assertEquals(projetoDto.getDescricao(), projeto.getDescricao());
	}

	@Test
	@DisplayName("Se a lista de todas as Materia Mock tiver elemento retorna um true, e se o id existe no banco")
	public void getMateriaId() {
		
		projetoRepository.save(createProjeto());

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(projetoId);

		assertThat(projetoDto).isNotEmpty();
		
		verify(projetoRepository, times(1)).findByMateriaId(projetoId);
	}

	@Test
	@DisplayName("Se a lista de todas as Materia Mock estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
	public void getNotMateriaId() {

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(notProjetoId);

		assertThat(projetoDto).isNullOrEmpty();
		
		verify(projetoRepository, times(1)).findByMateriaId(notProjetoId);
	}
	
	@Test
	@DisplayName("Se a lista de todos os Projeto Mock tiver elemento retorna um true")
	public void getProjetoAll() {
		
		projetoRepository.save(createProjeto());
		
		List<ProjetoDTO> projetoDto = projetoService.getAll();
		
		assertThat(projetoDto).isNotEmpty();
		
		verify(projetoRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Se a lista de todos os Projeto Mock estiver vazia ou nula deve retorna um False")
	public void getNotProjetoAll() {

		List<ProjetoDTO> projetoDto = projetoService.getAll();
		projetoDto.clear();
	
		assertThat(projetoDto).isNullOrEmpty();
		
		verify(projetoRepository, times(1)).findAll();
	}
}
