package com.academicquest.controller;

import static com.academicquest.components.UtilMock.Materia_ID;
import static com.academicquest.components.UtilMock.Materia_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosDTOTest.createMateriaDTO;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import com.academicquest.dto.MateriaDTO;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.service.MateriaService;
import com.academicquest.service.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MateriaControllerMockTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @MockBean
    private MateriaService materiaService;
    
	@Mock
	private MateriaRepository materiaRepository;
    
    @Autowired
    private MockMvc mockMvc;
	
    private List<MateriaDTO> materiaDTO;

    @BeforeEach
    public void setUp() throws Exception {
    	
        materiaDTO 	  = new ArrayList<>(of(createMateriaDTO()));
        mockMvc       = MockMvcBuilders.webAppContextSetup(context).build();

        when(materiaService.getByTurmaId(Materia_ID)).thenReturn(materiaDTO);
        when(materiaService.getByTurmaId(Materia_ID_NAO_EXISTE)).thenThrow(ResourceNotFoundException.class);

        when(materiaRepository.save(any())).thenReturn(materiaDTO);
        when(materiaService.getAll()).thenReturn(materiaDTO);
    }
    
    @Test
    @DisplayName("Buscar a materias da turma por id que nao existe e retorna 404")
    public void buscarTodasTurmasId() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/materias/turma/{id}", Materia_ID_NAO_EXISTE)
								        		.accept(MediaType.APPLICATION_JSON)
        				);
        resultActions.andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Buscar a materias da turma por id que existe, e retorna 200 e se os valores existe")
    public void buscarNotTodasTurmasId() throws Exception {
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
    @DisplayName("Testa a bucas de todas as materias e retorna uma lista com todas as materias")
    public void buscarTodasMaterias() throws Exception {
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
