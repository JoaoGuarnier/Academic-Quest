package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
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
import com.academicquest.service.exception.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class UserServiceTest {
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	private Long userId;
	private Long notUserId;

	@BeforeEach
	void setUpUserService() throws Exception {
		
		userId    = 1l;
		notUserId = 999l;
	}

	@Test
	@DisplayName("Deve traser por id um usuario")
	public void getUserId() {
		
		UserDTO turmaDto = userService.findById(userId);
		
		assertThat(turmaDto).isNotNull();
	}
	
	@Test
	@DisplayName("Deve mostra mensagem do exception, para o id que nao existe")
	public void getNotUserId() {
		
		Executable executable = () -> userService.findById(notUserId);
		
		Exception expectedEx = assertThrows(ResourceNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Entity not found"); 
	}
	
	@Test
	@DisplayName("Deve retorna uma page por os id")
	public void findPageUserAll() {
		
		PageRequest pageRequest = PageRequest.of(0,10);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Test
	@DisplayName("Deve retorna uma page vazia caso dos id")
	public void findNotPageUserAll() {
		
		PageRequest pageRequest = PageRequest.of(50,10);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
	
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
		
		UserDetails user = userService.loadUserByUsername("leon.codao@gmail.com");
		
		assertThat(user).isNotNull();
	}
}
