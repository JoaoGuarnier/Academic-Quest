package com.academicquest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TurmaControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private MockMvc mockMvc;
	

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc  = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/turmas")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
      //  System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].id").exists());
        resultActions.andExpect(jsonPath("$.[0].curso").exists());
        resultActions.andExpect(jsonPath("$.[0].semestre").exists());
        resultActions.andExpect(jsonPath("$.[0].complemento").exists());
    }
}
