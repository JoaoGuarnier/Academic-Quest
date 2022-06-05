package com.academicquest.controller;

import static java.util.List.of;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.academicquest.service.exception.ResourceNotFoundException;
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
	
    private Long existingId;
    private Long nonExistingId;
    private List<ProjetoDTO> projetoDTO;

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc         = MockMvcBuilders.webAppContextSetup(context).build();
    	
        existingId      = 1l;
        nonExistingId   = 999l;
        projetoDTO 		= new ArrayList<>(of(MockDadosDTOTest.createProjetoDTO()));

        when(projetoService.getByMateriaId(existingId)).thenReturn(projetoDTO);
        when(projetoService.getByMateriaId(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(projetoService.getAll()).thenReturn(projetoDTO);
        doNothing().when(projetoService).save(Mockito.any());
    }
   
    
    @Test
    public void findAllShould() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/projetos/materia/{id}", nonExistingId)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
     //   System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    public void findAll() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/projetos/materia/{id}", existingId)
								    			.accept(MediaType.APPLICATION_JSON)
	    			);
    	//System.out.println(resultActions.andDo(print()));
    	resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].descricao").exists());
        resultActions.andExpect(jsonPath("$.[0].status").exists());
        resultActions.andExpect(jsonPath("$.[0].materia").exists());
    }
    
    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/projetos")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
       // System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].descricao").exists());
        resultActions.andExpect(jsonPath("$.[0].status").exists());
        resultActions.andExpect(jsonPath("$.[0].materia").exists());
    }
    
    @Ignore
    public void saveShouldReturnProductDto() throws Exception {
    	
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
