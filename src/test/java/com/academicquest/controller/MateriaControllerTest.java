package com.academicquest.controller;

import static com.academicquest.components.UtilMock.Materia_ID;
import static com.academicquest.components.UtilMock.Materia_ID_NAO_EXISTE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MateriaControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private MockMvc mockMvc;
	
    @BeforeEach
    public void setUpMateriaController() throws Exception {
    	
        mockMvc      = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Ignore
    @DisplayName("Esse teste testar o endponit com resultado 404, se o id nao existe")
    public void buscarNotTurmasId() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/materias/turma/{id}", Materia_ID_NAO_EXISTE)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
        resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Esse teste testar o endponit com resultado 200, para trazer os valore por id ")
    public void buscarTurmasId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/materias/turma/{id}", Materia_ID)
								    			.accept(MediaType.APPLICATION_JSON)
	    			);
    	resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].professor").isNotEmpty());
    }
    
    @Test
    @DisplayName("Esse teste testar o endponit com resultado 200, para trazer uma lista com os valores de materias ")
    public void buscarListaMaterias() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/materias")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].nome").exists());
        resultActions.andExpect(jsonPath("$.[0].professor").isNotEmpty());
    }
}
