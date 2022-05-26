package com.academicquest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
    private Long existingId;
    private Long nonExistingId;
    private List<Long> listId = new ArrayList<>();
    private List<Long> nonExistinglistId = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception  {
    	existingId = 1L;
    	nonExistingId = 999L;
        listId.add(existingId);
    }
    
    @Test
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<User> user = repository.findById(1l);
    	
    	assertFalse(user.isPresent());
    }
    
    @Test
    @DisplayName("Deve salvar um User.")
    public void saveUser() {
    	
    	User user = MockDadosTest.createUser();
    	
    	user = repository.save(user);

        assertNotNull(user);
    }
    
    @Test
    @DisplayName("Deve lanca uma exception quando nao existir  o resultado no banco")
    public void deletaELancaException() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(nonExistingId);
        });
    }
    
  

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdNotExistir() {
    	
        Optional<User> optionalUser = repository.findById(nonExistingId);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir3() {
    	
    	User user = repository.save(MockDadosTest.createUser());
    	
    	Optional<User> optionalUser = repository.findById(user.getId());
    	
    	assertTrue(optionalUser.isPresent());
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void findByTurmaIdNotExistir() {
    	
    	List<User> optionalMateria = repository.findByIdIn(nonExistinglistId);
    	assertThat(optionalMateria).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<User> optionalUser = repository.findByIdIn(listId);
    	assertThat(optionalUser).isNotEmpty();
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void findByEmailNotExistir() {
    	
    	User optionalUser = repository.findByEmail("Mateus@gmail.com");
    	assertThat(optionalUser).isNull();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByEmailExistir() {
    	
    	User optionalUser = repository.findByEmail("joao@gmail.com");
    	assertThat(optionalUser).isNotNull();
    }
}
