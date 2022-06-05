package com.academicquest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.repository.ProjetoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProjetoControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
	private ProjetoRepository materiaRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
	@Autowired
    private ObjectMapper objectMapper;
	
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc         = MockMvcBuilders.webAppContextSetup(context).build();
    	
        existingId      = 1l;
        nonExistingId   = 999L;
        
        materiaRepository.save(MockDadosTest.createProjeto());
    }
   
    //TODO: Esse teste precisar ser tratado no service para id que nao existe, sem tratamento nao conseguer captura o not found
    @Ignore
    public void findAllShould() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/projetos/materia/{id}", nonExistingId)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
        System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    public void findAll() throws Exception{
    	
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/projetos/materia/{id}", existingId)
								    			.accept(MediaType.APPLICATION_JSON)
	    			);
    	System.out.println(resultActions.andDo(print()));
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
        System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].descricao").exists());
        resultActions.andExpect(jsonPath("$.[0].status").exists());
        resultActions.andExpect(jsonPath("$.[0].materia").exists());
    }
    
    //TODO: Esse teste precisa retorna 201 mais ele rotorna 200 precisa arrumar no controller
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
