package com.academicquest.controller;

import static com.academicquest.components.UtilMock.Grupo_ID;
import static com.academicquest.components.UtilMock.Grupo_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosDTOTest.createGrupoUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.service.exception.GrupoNaoEncontradoException;
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
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
    private GrupoUpdateDTO grupoUpdateDTO;

    @BeforeEach
    public void setUpGrupoController() throws Exception {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
        grupoUpdateDTO  = createGrupoUpdateDTO();
    }
    
    @Test
    @DisplayName("Testar o update do grupos e retorna 200 se o id existir e os valores estiverem certos")
    public void updateGrupo() throws Exception {
    	
        String jsonBody = objectMapper.writeValueAsString(grupoUpdateDTO);
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/grupos/{id}", Grupo_ID)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.alunoLiderId").exists());
        resultActions.andExpect(jsonPath("$.listaAlunosId").exists());
    }
    
    @Test
    @DisplayName("Testar o update do grupos e retorna 404 se tiver o id nao existe")
    public void updateNotGrupo() throws Exception, NestedServletException {
    	
		String jsonBody = objectMapper.writeValueAsString(MockDadosDTOTest.createGrupoUpdateDTO());

				mockMvc.perform(MockMvcRequestBuilders
			            .put("/grupos/{id}", 999L)
			            .content(jsonBody)
			            .contentType(MediaType.APPLICATION_JSON)
			            .accept(MediaType.APPLICATION_JSON))
			            .andExpect(status().isNotFound()
            		);
    }
    
    //Libera essa notação depois de fazer a liberação no controller do tratamento do 200 para 201
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
								        		.get("/grupos/materia/{id}", Grupo_ID)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
       //System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").value(MockDadosDTOTest.createGrupoMateriaDTO().getId()));
        resultActions.andExpect(jsonPath("$.[0].nome").value(MockDadosDTOTest.createGrupoMateriaDTO().getNome()));
    }
    
    //TODO: Esse teste precisar ser tratado no service para id que nao existe, sem tratamento nao conseguer captura o not found
    @Ignore
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotMateriaId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/materia/{id}", Grupo_ID_NAO_EXISTE)
								    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Testa o retorno do buscaAlunosMateriaId se tras infomações dos materias dos alunos e se o velor é existente")
    public void buscarAlunosMateriaId() throws Exception{
    	ResultActions resultActions = mockMvc.perform(
							    			MockMvcRequestBuilders
								    			.get("/grupos/alunos/materia/{id}", 1L)
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
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotAlunosMateriaId() throws Exception {
    	ResultActions resultActions = mockMvc.perform(
										MockMvcRequestBuilders
							    			.get("/grupos/alunos/materia/{id}", Grupo_ID_NAO_EXISTE)
							    			.accept(MediaType.APPLICATION_JSON)
    			);
    	resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testa o retorno do getbyId se tras infomações dos grupos e se o velor é existente")
    public void buscarGrupoId() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/grupos/{id}", Grupo_ID)
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.listaAlunos").exists());
        resultActions.andExpect(jsonPath("$.alunoLiderId").exists());
    }

    @Ignore
    @DisplayName("Testar se o id nao existente retorna a mensagem da exception e se é 404")
    public void buscarNotGrupoId() throws Exception, GrupoNaoEncontradoException {
    	
        		mockMvc.perform(
		    			MockMvcRequestBuilders.
		    			get("/grupos/{id}", 999l)
		    			.accept(MediaType.APPLICATION_JSON))
		    			.andExpect(status().isNotFound()
		    			);
		    }
        
}
