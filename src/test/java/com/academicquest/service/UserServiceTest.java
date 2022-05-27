package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
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
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class UserServiceTest {
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1l;
		nonExistingId = 999l;
	}

	@Test
	public void findById() {
		
		UserDTO turmaDto = userService.findById(existingId);
		
		assertThat(turmaDto).isNotNull();
	}
	
	@Test
	public void getByNotMId() {
		
		Executable executable = () -> userService.findById(nonExistingId);
		
		Exception expectedEx = assertThrows(ResourceNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Entity not found"); 
	}
	
	@Test
	public void findAll() {
		
		PageRequest pageRequest = PageRequest.of(0,10);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Test
	public void findNotAll() {
		PageRequest pageRequest = PageRequest.of(50,10);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
	
		assertThat(projetoDto).isNullOrEmpty();
	}
	
	@Test
	public void loadUserNotByUsername() {
		
		Executable executable = () -> userService.loadUserByUsername("teste");
		
		Exception expectedEx = assertThrows(UsernameNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "User not found"); 
	}
	
	@Test
	public void loadUserByUsername() {
		
		userRepository.save(MockDadosTest.createUser());
		
		UserDetails user = userService.loadUserByUsername("leon.codao@gmail.com");
		
		assertThat(user).isNotNull();
	}
}
