package com.academicquest.controller;

import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoMateriaDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
import static com.academicquest.mockDados.MockDadosDTOTest.createUserDTO;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
	
    private Long grupoId;
    private Long notGrupoId;
    private UserDTO userDTO;
    private GrupoDTO grupoDTO;
    private GrupoMateriaDTO grupoMateriaDTO;
    private GrupoUpdateDTO grupoUpdateDTO;

    @BeforeEach
    public void setUpGrupoControllerMock() throws Exception {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
        grupoId     	= 1l;
        notGrupoId      = 999l;
        userDTO         = createUserDTO();
        grupoDTO        = createGrupoDTO();
        grupoMateriaDTO = createGrupoMateriaDTO();
        grupoUpdateDTO  = createGrupoUpdateDTO();

        when(grupoService.getByMateriaId(grupoId)).thenReturn(of(grupoMateriaDTO));
        when(grupoService.getByMateriaId(notGrupoId)).thenThrow(ResourceNotFoundException.class);

        when(grupoService.buscarAlunosSemGrupo(grupoId)).thenReturn(of(userDTO));
        when(grupoService.buscarAlunosSemGrupo(notGrupoId)).thenThrow(ResourceNotFoundException.class);
        
        when(grupoService.getById(grupoId)).thenReturn(grupoDTO);
        doThrow(ResourceNotFoundException.class).when(grupoService).getById(notGrupoId);

        when(grupoService.updateGrupo(any(), eq(grupoId))).thenReturn(grupoUpdateDTO);
        when(grupoService.updateGrupo(any(), eq(notGrupoId))).thenThrow(ResourceNotFoundException.class);

        doNothing().when(grupoService).save(Mockito.any(GrupoPostDTO.class));
    }
    
    @Test
    @DisplayName("Testar o update do grupos e retorna 200 se o id existir e os valores estiverem certos")
    public void updateGrupo() throws Exception {
    	
        String jsonBody = objectMapper.writeValueAsString(grupoUpdateDTO);
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", grupoId)
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
    @DisplayName("Testar o update do grupos e retorna 404 se tiver o id nao existe")
    public void updateNotGrupo() throws Exception {

    	GrupoUpdateDTO grupoUpdatetDTO = MockDadosDTOTest.createGrupoUpdateDTO();

        String jsonBody = objectMapper.writeValueAsString(grupoUpdatetDTO);
        
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", notGrupoId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions.andExpect(status().isNotFound());
    }
    //Libera essa notação depois de fazer a liberaçãono controller
    @Ignore
    @DisplayName("Testar o save do grupos e retorna 201 se tiver funcional o save")
    public void saveGrupo() throws Exception {
    	
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
    @DisplayName("Testa o retorno do findByMateriaId se tras infomações dos materias e se o velor é existente")
    public void buscarMateriaId() throws Exception{
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/materia/{id}", grupoId)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").value(grupoDTO.getId()));
        resultActions.andExpect(jsonPath("$.[0].nome").value(grupoDTO.getNome()));
    }
    
    @Test
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotMateriaId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/materia/{id}", notGrupoId)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Testa o retorno do buscaAlunosMateriaId se tras infomações dos materias dos alunos e se o velor é existente")
    public void buscarAlunosMateriaId() throws Exception{
    	
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/alunos/materia/{id}", grupoId)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isOk());
    	resultActions.andExpect(jsonPath("$.[0].id").value(grupoDTO.getId()));
    	resultActions.andExpect(jsonPath("$.[0].firstName").value(userDTO.getFirstName()));
    	resultActions.andExpect(jsonPath("$.[0].lastName").value(userDTO.getLastName()));
    	resultActions.andExpect(jsonPath("$.[0].email").value(userDTO.getEmail()));
    	resultActions.andExpect(jsonPath("$.[0].roles").isNotEmpty());
    }
    
    @Test
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotAlunosMateriaId() throws Exception {
    	ResultActions resultActions = mockMvc.perform(
										MockMvcRequestBuilders
							    			.get("/grupos/alunos/materia/{id}", notGrupoId)
							    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testa o retorno do getbyId se tras infomações dos grupos e se o velor é existente")
    public void buscarGrupoId() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/{id}", grupoId)
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.alunos").exists());
        resultActions.andExpect(jsonPath("$.alunoLiderId").exists());
    }

    @Test
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotGrupoId() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/{id}", notGrupoId)
								        		.accept(MediaType.APPLICATION_JSON)
        		);
        resultActions.andExpect(status().isNotFound());
    }
}
