package com.academicquest.service;

import static com.academicquest.components.UtilMock.User_ID;
import static com.academicquest.mockDados.MockDadosTest.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.UserDTO;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@SpringBootTest
@Transactional
public class UserServiceTest {
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Test
	@DisplayName("Deve traser por id um usuario")
	public void getUserId() {
		
		UserDTO turmaDto = userService.buscarPorId(User_ID);
		
		assertThat(turmaDto).isNotNull();
	}
	
	@Test
	@DisplayName("Deve mostra mensagem do exception, para o id que nao existe")
	public void getNotUserId() {
		
		Executable executable = () -> userService.buscarPorId(User_ID);
		
		Exception expectedEx = assertThrows(GrupoNaoEncontradoException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Entity not found"); 
	}
	
	@Test
	@DisplayName("Deve retorna uma page por os id")
	public void findPageUserAll() {
		
		PageRequest pageRequest = PageRequest.of(0,10);
		
		Page<UserDTO> projetoDto = userService.buscarTodos(pageRequest);
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Deve retorna uma page vazia caso dos id")
	public void findNotPageUserAll() {
		
		PageRequest pageRequest = PageRequest.of(50,10);
		
		Page<UserDTO> projetoDto = userService.buscarTodos(pageRequest);
	
		assertThat(projetoDto).isNullOrEmpty();
	}
	
	@Test
	@DisplayName("Deve lançar uma mensagem da exception, para quando não existe  email")
	public void getNotUserUsernameEmail() {
		
		Executable executable = () -> userService.loadUserByUsername("teste");
		
		Exception expectedEx = assertThrows(UsernameNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "User not found"); 
	}
	
	@Test
	@DisplayName("Deve traser o email existente")
	public void getUserUsernameEmail() {
		
		userRepository.save(createUser());
		
		UserDetails user = userService.loadUserByUsername("leon.Joao@gmail.com");
		
		assertThat(user).isNotNull();
	}
}
