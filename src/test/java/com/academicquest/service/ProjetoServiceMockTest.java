package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosDTOTest.createProjetoPostDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Projeto;
import com.academicquest.repository.ProjetoRepository;

@ExtendWith(SpringExtension.class)
public class ProjetoServiceMockTest {

	@InjectMocks
	private ProjetoService projetoService;
	
	@Mock
	private ProjetoRepository projetoRepository;
	
	private long existingId;
	private long nonExistingId;
	private Projeto projeto;
	private List<Projeto> a;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999;
		projeto = MockDadosTest.createProjeto();
		a = new ArrayList<>(List.of(projeto));

		Mockito.doReturn(List.of()).when(projetoRepository).findAll();
		Mockito.when(projetoRepository.findAll()).thenReturn(a);

		Mockito.doReturn(List.of(projeto)).when(projetoRepository).findyByMateriaId(existingId);
		Mockito.doReturn(List.of()).when(projetoRepository).findyByMateriaId(nonExistingId);
		
		Mockito.when(projetoRepository.save(projeto)).thenReturn(projeto);
	}

	@Ignore
	public void deleteShouldDeleteResoucerWhenIdExist() {

		ProjetoPostDTO projetoDto = createProjetoPostDTO();

		//Projeto projeto = projetoRepository.save(createProjeto());

		projetoService.save(projetoDto);
		
//		assertEquals(projetoDto.getNome(),      projeto.getNome());
//		assertEquals(projetoDto.getIdMateria(), projeto.getMateria().getId());
//		assertEquals(projetoDto.getDescricao(), projeto.getDescricao());
	}

	@Test
	public void getNotByTurmaId() {
		
		projetoRepository.save(MockDadosTest.createProjeto());

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(existingId);

		assertThat(projetoDto).isNotEmpty();
		verify(projetoRepository, Mockito.times(1)).findyByMateriaId(existingId);
	}

	@Test
	public void getByTurmaId() {

		List<ProjetoDTO> projetoDto = projetoService.getByMateriaId(nonExistingId);

		assertThat(projetoDto).isNullOrEmpty();
		verify(projetoRepository, Mockito.times(1)).findyByMateriaId(nonExistingId);
	}
	
	@Test
	public void getNotAll() {
		
		projetoRepository.save(MockDadosTest.createProjeto());
		
		List<ProjetoDTO> projetoDto = projetoService.getAll();
		
		assertThat(projetoDto).isNotEmpty();
		verify(projetoRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void getAll() {

		List<ProjetoDTO> projetoDto = projetoService.getAll();
		projetoDto.clear();
	
		assertThat(projetoDto).isNullOrEmpty();
		verify(projetoRepository, Mockito.times(1)).findAll();
	}
}
