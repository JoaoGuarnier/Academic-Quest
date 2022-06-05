package com.academicquest.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.web.util.NestedServletException;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.repository.GrupoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class GrupoControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
    private Long existingId;
    private Long nonExistingId;
    private GrupoDTO grupoDTO;
    private GrupoUpdateDTO grupoUpdateDTO;

    @BeforeEach
    void setUp() throws Exception {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
        existingId      = 1l;
        nonExistingId   = 999l;
        grupoRepository.save(MockDadosTest.createGrupo());
        grupoDTO        = MockDadosDTOTest.createGrupoDTO();
        grupoUpdateDTO  = MockDadosDTOTest.createGrupoUpdateDTO();
    }
    
    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    	
        String jsonBody = objectMapper.writeValueAsString(grupoUpdateDTO);
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", existingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.idAlunoLider").exists());
        resultActions.andExpect(jsonPath("$.idAlunos").exists());
    }
    
    @Test
    public void updateShouldReturnProductDtoWhenIdExists() throws Exception, NestedServletException {
    	
		String jsonBody = objectMapper.writeValueAsString(MockDadosDTOTest.createGrupoUpdateDTO());

		assertThrows(NestedServletException.class, () -> {
				mockMvc.perform(MockMvcRequestBuilders
			            .put("/grupos/{id}", 999L)
			            .content(jsonBody)
			            .contentType(MediaType.APPLICATION_JSON)
			            .accept(MediaType.APPLICATION_JSON))
			            .andExpect(status().isNotFound()
            		);
		});
    }
    
    //Libera essa notação depois de fazer a liberação no controller do tratamento do 200 para 201
    @Ignore
    public void saveShouldReturnProductDto() throws Exception {
    	
        String jsonBody = objectMapper.writeValueAsString(MockDadosDTOTest.createGrupoPostDTO());
        
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.post("/grupos")
								        		.content(jsonBody)
								        		.contentType(MediaType.APPLICATION_JSON)
								        		);
        resultActions.andExpect(status().isCreated());
    }
    
    @Test
    public void findAllShould() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/materia/{id}", existingId)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
       //System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").value(grupoDTO.getId()));
        resultActions.andExpect(jsonPath("$.[0].nome").value(grupoDTO.getNome()));
    }
    
    //TODO: Esse teste precisar ser tratado no service para id que nao existe, sem tratamento nao conseguer captura o not found
    @Ignore
    public void findAll() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/materia/{id}", nonExistingId)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    public void getByMateriaId() throws Exception{
    	
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/alunos/materia/{id}", 2l)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isOk());
    	resultActions.andExpect(jsonPath("$.[0].id").exists());
    	resultActions.andExpect(jsonPath("$.[0].firstName").exists());
    	resultActions.andExpect(jsonPath("$.[0].lastName").exists());
    	resultActions.andExpect(jsonPath("$.[0].email").exists());
    	resultActions.andExpect(jsonPath("$.[0].roles").exists());
    }
    
    //TODO: Esse teste precisar ser tratado no service para id que nao existe, sem tratamento nao conseguer captura o not found
    @Ignore
    public void getByNotMateriaId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
										MockMvcRequestBuilders
							    			.get("/grupos/alunos/materia/{id}", nonExistingId)
							    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/{id}", existingId)
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.alunos").exists());
        resultActions.andExpect(jsonPath("$.alunoLiderId").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception, NestedServletException {
    	
        assertThrows(NestedServletException.class, () -> {
        		mockMvc.perform(
		    			MockMvcRequestBuilders.
		    			get("/grupos/{id}", 9l)
		    			.accept(MediaType.APPLICATION_JSON))
		    			.andExpect(status().isNotFound()
		    			);
		    });
        }
}
