package com.academicquest.controller;

import static java.util.List.of;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import com.academicquest.dto.TurmaDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.service.TurmaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TurmaControllerMockTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @MockBean
    private TurmaService turmaService;
    
    
    @Autowired
    private MockMvc mockMvc;
	
    private List<TurmaDTO> turmaDTO;

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc  = MockMvcBuilders.webAppContextSetup(context).build();
        turmaDTO = new ArrayList<>(of(MockDadosDTOTest.createTurmaDTO()));

        when(turmaService.getAll()).thenReturn(turmaDTO);
    }
    
    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/turmas")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].curso").exists());
        resultActions.andExpect(jsonPath("$.[0].semestre").exists());
        resultActions.andExpect(jsonPath("$.[0].complemento").exists());
    }
}
