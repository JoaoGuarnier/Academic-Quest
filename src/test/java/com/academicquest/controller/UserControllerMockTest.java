package com.academicquest.controller;

import static com.academicquest.components.UtilMock.User_ID;
import static com.academicquest.components.UtilMock.User_ID_NAO_EXISTE;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.academicquest.dto.UserDTO;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.service.UserService;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerMockTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private MockMvc mockMvc;
	
    private PageImpl<UserDTO> userPageDTO;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() throws Exception {
    	
    	mockMvc       = MockMvcBuilders.webAppContextSetup(context).build();
        userPageDTO   = new PageImpl<>(of(MockDadosDTOTest.createUserDTO()));
        userDTO   = MockDadosDTOTest.createUserDTO();
        
        when(userService.buscarPorId(User_ID)).thenReturn(userDTO);
        doThrow(GrupoNaoEncontradoException.class).when(userService).buscarPorId(User_ID_NAO_EXISTE);

        when(userService.buscarTodos(any())).thenReturn(userPageDTO);
    }
    
    @Test
    public void findByIdWhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/users")
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
//        System.out.println(resultActions.andDo(print()));
        
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
								        		.get("/users/{id}", User_ID)
								        		.accept(MediaType.APPLICATION_JSON)
		        		);
        //System.out.println(resultActions.andDo(print()));
        resultActions.andExpect(status().isOk());
        
        resultActions.andExpect(jsonPath("$.id")       .exists());
        resultActions.andExpect(jsonPath("$.firstName").exists());
        resultActions.andExpect(jsonPath("$.lastName") .exists());
        
        resultActions.andExpect(jsonPath("$.roles").isNotEmpty());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(
							        		MockMvcRequestBuilders
								        		.get("/users/{id}", User_ID_NAO_EXISTE)
								        		.accept(MediaType.APPLICATION_JSON)
        		);
        resultActions.andExpect(status().isNotFound());
    }
}
