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
public class UserControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private MockMvc mockMvc;
	
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc       = MockMvcBuilders.webAppContextSetup(context).build();
    	
    	existingId    = 1l;
    	nonExistingId = 999l;
    }
    
    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/users")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
     //   System.out.println(resultActions.andDo(print()));
        
        resultActions.andExpect(status().isOk());
        
        resultActions.andExpect(jsonPath("$.last")      	  .exists());
        resultActions.andExpect(jsonPath("$.size")			  .exists());
        resultActions.andExpect(jsonPath("$.sort") 			  .exists());
        resultActions.andExpect(jsonPath("$.first")			  .exists());
        resultActions.andExpect(jsonPath("$.empty")			  .exists());
        resultActions.andExpect(jsonPath("$.number")		  .exists());
        resultActions.andExpect(jsonPath("$.pageable")        .exists());
        resultActions.andExpect(jsonPath("$.totalPages")	  .exists());
        resultActions.andExpect(jsonPath("$.totalElements")   .exists());
        resultActions.andExpect(jsonPath("$.numberOfElements").exists());
        
        resultActions.andExpect(jsonPath("$.content")		  .isNotEmpty());
    }
    
    @Test
    public void findByIdWhenId() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/users/{id}", existingId)
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
      //  System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        
        resultActions.andExpect(jsonPath("$.id")       .exists());
        resultActions.andExpect(jsonPath("$.lastName") .exists());
        resultActions.andExpect(jsonPath("$.firstName").exists());
        
        resultActions.andExpect(jsonPath("$.roles").isNotEmpty());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/users/{id}", nonExistingId)
								        		.accept(MediaType.APPLICATION_JSON)
        		);
        resultActions.andExpect(status().isNotFound());
    }
}
