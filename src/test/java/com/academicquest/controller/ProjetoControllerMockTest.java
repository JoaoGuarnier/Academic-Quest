package com.academicquest.controller;

import static com.academicquest.components.UtilMock.PROJETO_ID;
import static com.academicquest.components.UtilMock.PROJETO_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosDTOTest.createProjetoDTO;
import static java.util.List.of;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.service.ProjetoService;
import com.academicquest.service.exception.MateriaNaoEncontradaException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjetoControllerMockTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @MockBean
    private ProjetoService projetoService;
    
	@Mock
	private MateriaRepository materiaRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
	@Autowired
    private ObjectMapper objectMapper;
	
    private List<ProjetoDTO> projetoDTO;
    private ProjetoDTO projeto;

    @BeforeEach
    public void setUpProjetoControllerMock() throws Exception {
    	
    	mockMvc    = MockMvcBuilders.webAppContextSetup(context).build();
    	
        projetoDTO = new ArrayList<>(of(createProjetoDTO()));
        projeto    = MockDadosDTOTest.createProjetoDTO();

        when(projetoService.buscarPorMateriaId(PROJETO_ID)).thenReturn(projetoDTO);
        when(projetoService.buscarPorMateriaId(PROJETO_ID_NAO_EXISTE)).thenThrow(MateriaNaoEncontradaException.class);

        when(projetoService.buscarTodos()).thenReturn(projetoDTO);
		when(projetoService.salvar(ArgumentMatchers.any())).thenReturn(projeto);
    }
   
    @Test
    @DisplayName("Buscar os projetos por materias id que nao existe no banco e retorna um 404")
    public void BuscarNotProjetoMateriaId() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/projetos/materia/{id}", PROJETO_ID_NAO_EXISTE)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
        resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Buscar os projetos por materias id e retornar 200 e se os valores existe")
    public void BuscarProjetoMateriaId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/projetos/materia/{id}", PROJETO_ID)
								    			.accept(MediaType.APPLICATION_JSON)
	    			);
    	resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].descricao").exists());
        resultActions.andExpect(jsonPath("$.[0].status").exists());
        resultActions.andExpect(jsonPath("$.[0].materia").exists());
    }
    
    @Test
    @DisplayName("Buscar os projetos por materias id e retornar 200 e se os valores existe")
    public void buscarListaProjetos() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/projetos")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].descricao").exists());
        resultActions.andExpect(jsonPath("$.[0].status").exists());
        resultActions.andExpect(jsonPath("$.[0].materia").exists());
    }
    
    @Ignore
    @DisplayName("Testa o save e retorna o 201 se todos os valores estiverem certo")
    public void saveProjeto() throws Exception {
    	
        String jsonBody = objectMapper.writeValueAsString(MockDadosDTOTest.createProjetoPostDTO());
        
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.post("/projetos")
								        		.content(jsonBody)
								        		.contentType(MediaType.APPLICATION_JSON)
								        		);
        resultActions.andExpect(status().isCreated());
    }
    
}
