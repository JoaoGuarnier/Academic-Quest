package com.academicquest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.service.GrupoService;
import com.academicquest.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GrupoControllerMockTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @MockBean
    private GrupoService grupoService;
    
    @Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
    private Long existingId;
    private Long nonExistingId;
    private GrupoMateriaDTO grupoMateriaDTO;
    private UserDTO userDTO;
    private GrupoDTO grupoDTO;
    private GrupoUpdateDTO grupoUpdateDTO;

    @BeforeEach
    void setUp() throws Exception {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
        existingId      = 1l;
        nonExistingId   = 999l;
        userDTO         = MockDadosDTOTest.createUserDTO();
        grupoDTO        = MockDadosDTOTest.createGrupoDTO();
        grupoMateriaDTO = MockDadosDTOTest.createGrupoMateriaDTO();
        grupoUpdateDTO  = MockDadosDTOTest.createGrupoUpdateDTO();

        when(grupoService.getByMateriaId(existingId)).thenReturn(List.of(grupoMateriaDTO));
        when(grupoService.getByMateriaId(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(grupoService.buscarAlunosSemGrupo(existingId)).thenReturn(List.of(userDTO));
        when(grupoService.buscarAlunosSemGrupo(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        
        when(grupoService.getById(existingId)).thenReturn(grupoDTO);
        doThrow(ResourceNotFoundException.class).when(grupoService).getById(nonExistingId);

        when(grupoService.updateGrupo(any(), eq(existingId))).thenReturn(grupoUpdateDTO);
        when(grupoService.updateGrupo(any(), eq(nonExistingId))).thenThrow(ResourceNotFoundException.class);

        doNothing().when(grupoService).save(Mockito.any(GrupoPostDTO.class));
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
    //Libera essa notação depois de fazer a liberaçãono controller
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
    
    @Test
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
								    			.get("/grupos/alunos/materia/{id}", existingId)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	//System.out.println(resultActions.andDo(print()));
    	resultActions.andExpect(status().isOk());
    	resultActions.andExpect(jsonPath("$.[0].id").value(grupoDTO.getId()));
    	resultActions.andExpect(jsonPath("$.[0].firstName").value(userDTO.getFirstName()));
    	resultActions.andExpect(jsonPath("$.[0].lastName").value(userDTO.getLastName()));
    	resultActions.andExpect(jsonPath("$.[0].email").value(userDTO.getEmail()));
    	resultActions.andExpect(jsonPath("$.[0].roles").isNotEmpty());
    }
    
    @Test
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
     //   System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.alunos").exists());
        resultActions.andExpect(jsonPath("$.alunoLiderId").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/{id}", nonExistingId)
								        		.accept(MediaType.APPLICATION_JSON)
        		);
        resultActions.andExpect(status().isNotFound());
    }
}
