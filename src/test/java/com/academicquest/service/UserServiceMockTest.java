package com.academicquest.service;

import static com.academicquest.mockDados.MockDadosTest.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academicquest.dto.UserDTO;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.User;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

@ExtendWith(SpringExtension.class)
public class UserServiceMockTest {
	
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	
	private long userId;
	private long notUserId;
	private String userEmail;
	private String notUserEmail;
    private PageImpl<User> page;
	private User user;
	
	@BeforeEach
	public void setUpUserServiceMock() throws Exception {
		
		userId       = 1L;
		notUserId    = 999;
		notUserEmail = "test";
		user         = createUser();
		page         = new PageImpl<>(List.of(user));
		userEmail    = MockDadosTest.createUser().getEmail();
		
        when(userRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doThrow(GrupoNaoEncontradoException.class).when(userRepository).findById(notUserId);
		
		doReturn(user).when(userRepository).findByEmail(userEmail);
		
		doThrow(UsernameNotFoundException.class).when(userRepository).findByEmail(notUserEmail);

		when(userRepository.save(any())).thenReturn(user);
	}

	@Test
	@DisplayName("Deve traser por id um usuario Mock")
	public void getUserId() {
		
		UserDTO turmaDto = userService.buscarPorId(userId);
		
		assertThat(turmaDto).isNotNull();
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	@DisplayName("Deve mostra mensagem do exception Mock, para o id que nao existe")
	public void getNotUserId() {
		
		assertThrows(GrupoNaoEncontradoException.class, () -> {
			userService.buscarPorId(notUserId);
			});
		verify(userRepository, times(1)).findById(notUserId);
	}
	
	@Test
	@DisplayName("Deve retorna uma page por os id Mock")
	public void findPageUserAll() {
		
		PageRequest pageRequest  = PageRequest.of(0,10);
		
		Page<UserDTO> projetoDto = userService.buscarTodos(pageRequest);
		
		assertThat(projetoDto).isNotEmpty();
		verify(userRepository, times(1)).findAll(pageRequest);
	}
	
	@Test
	@DisplayName("Deve lançar uma mensagem da exception Mock, para quando não existe email")
	public void getNotUserUsernameEmail() {
		
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername(notUserEmail);
			});
		
		verify(userRepository, times(1)).findByEmail(notUserEmail);
	}
	
	@Test
	@DisplayName("Deve traser o email existente Mock")
	public void getUserUsernameEmail() {
		
		userRepository.save(createUser());
		
		UserDetails user = userService.loadUserByUsername(userEmail);
		
		assertThat(user).isNotNull();
		verify(userRepository, times(1)).findByEmail(userEmail);
	}
}
