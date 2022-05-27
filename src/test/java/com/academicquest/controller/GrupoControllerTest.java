package com.academicquest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GrupoControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1l;
        nonExistingId = 999l;
        countTotalProducts = 25l;
    }
    
//    @Test
//    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
//
//    	GrupoUpdateDTO grupoUpdatetDTO = MockDadosTest.createGrupoUpdateDTO();
//        String jsonBody = objectMapper.writeValueAsString(grupoUpdatetDTO);
//        ResultActions resultActions =
//                mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .put("/grupos/{id}", nonExistingId)
//                                .content(jsonBody)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                );
//
//        resultActions.andExpect(status().isNotFound());
//    }
}
