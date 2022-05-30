package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static com.academicquest.mockDados.MockDadosTest.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.UserDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Materia;
import com.academicquest.model.User;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class UserServiceMockTest {
	
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	
	private long existingId;
	private long nonExistingId;
    private PageImpl<User> page;
	private User user;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999;
		user = createUser();
		page = new PageImpl<>(List.of(user));

        Mockito.when(userRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(existingId);
		Mockito.doReturn(Optional.empty()).when(userRepository).findById(nonExistingId);
		
		Mockito.doReturn(user).when(userRepository).findByEmail("aaaaa");
		Mockito.doReturn(user).when(userRepository).findByEmail("qqqqqq");

		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
	}

//	@Test
//	public void findById() {
//		
//		UserDTO turmaDto = userService.findById(existingId);
//		
//		assertThat(turmaDto).isNotNull();
//	}
//	
//	@Test
//	public void getByNotMId() {
//		
//		Executable executable = () -> userService.findById(nonExistingId);
//		
//		Exception expectedEx = assertThrows(ResourceNotFoundException.class, executable);
//		
//		assertEquals(expectedEx.getMessage(), "Entity not found"); 
//	}
	
	@Test
	public void findAll() {
		
		PageRequest pageRequest = PageRequest.of(0,10);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
		
		assertThat(projetoDto).isNotEmpty();
	}
	
	@Ignore
	public void findNotAll() {
		PageRequest pageRequest = PageRequest.of(150,101);
		
		Page<UserDTO> projetoDto = userService.findAll(pageRequest);
	
		assertThat(projetoDto).isNullOrEmpty();
	}
	
//	@Test
//	public void loadUserNotByUsername() {
//		
//		Executable executable = () -> userService.loadUserByUsername("teste");
//		
//		Exception expectedEx = assertThrows(UsernameNotFoundException.class, executable);
//		
//		assertEquals(expectedEx.getMessage(), "User not found"); 
//	}
//	
//	@Test
//	public void loadUserByUsername() {
//		
//		userRepository.save(MockDadosTest.createUser());
//		
//		UserDetails user = userService.loadUserByUsername("leon.codao@gmail.com");
//		
//		assertThat(user).isNotNull();
//	}
}
