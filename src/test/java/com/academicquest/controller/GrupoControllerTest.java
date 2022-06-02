package com.academicquest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GrupoControllerTest {
	
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
    private Long existingId;
    private Long nonExistingId;
    private GrupoPostDTO grupoPostDTO;

    @BeforeEach
    void setUp() throws Exception {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	grupoPostDTO = MockDadosDTOTest.createGrupoPostDTO();
        existingId = 1l;
        nonExistingId = 999l;
    }
    
    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    	
    	GrupoUpdateDTO grupoUpdatetDTO = MockDadosDTOTest.createGrupoUpdateDTO();
    	
        Long expectedId = grupoUpdatetDTO.getIdAlunoLider();
        String expectedName = grupoUpdatetDTO.getNome();
        List<Long> expectedDescription = grupoUpdatetDTO.getIdAlunos();
    	
        String jsonBody = objectMapper.writeValueAsString(grupoUpdatetDTO);
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", existingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.nome").value(expectedName));
        resultActions.andExpect(jsonPath("$.idAlunoLider").value(expectedId));
        resultActions.andExpect(jsonPath("$.idAlunos.*").value(expectedDescription.size()));
    }
    
    @Ignore
    public void updateShouldReturnProductDtoWhenIdExists() throws Exception {

    	GrupoUpdateDTO grupoUpdatetDTO = MockDadosDTOTest.createGrupoUpdateDTO();

        String jsonBody = objectMapper.writeValueAsString(grupoUpdatetDTO);
        
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", nonExistingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions.andExpect(status().isNotFound());
    }
    
    @Ignore
   // @WithMockUser(username = "ADMIN", password = "OPERATOR_OR_ADMIN", roles = "PUBLIC")
//    @WithAnonymousUser
//    @WithUserDetails
    public void saveShouldReturnProductDto() throws Exception {
    	
    	
    	
        String jsonBody = objectMapper.writeValueAsString(grupoPostDTO);
        
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.post("http://localhost:8080/grupos/")
								        		.content(jsonBody)
								        		.contentType(MediaType.APPLICATION_JSON))
								        		.andDo(print())
								        	    .andExpect(status().isCreated());
        resultActions.andExpect(status().isCreated());
    }
}
