package com.academicquest.repository;

import static com.academicquest.mockDados.MockDadosTest.createUser;
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

import com.academicquest.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
    private Long UserId;
    private Long notUserId;
    private List<Long> listUserId = new ArrayList<>();
    private List<Long> notlistUserId = new ArrayList<>();

    @BeforeEach
    public void setUpUser() throws Exception  {
    	UserId = 1L;
    	notUserId = 999L;
        listUserId.add(UserId);
    }
    
    @Test
    @DisplayName("Deve deleta uma User quando tiver no banco")
    public void deleteUserExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<User> user = repository.findById(1l);
    	
    	assertFalse(user.isPresent());
    }
    
    @Test
    @DisplayName("Deve salvar um User.")
    public void saveUser() {
    	
    	User user = createUser();
    	
    	user = repository.save(user);

        assertNotNull(user);
    }
    
    @Test
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaUserNaoExistente() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(notUserId);
        });
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdUserNotExistente() {
    	
        Optional<User> optionalUser = repository.findById(notUserId);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdUserExistente() {
    	
    	User user = repository.save(createUser());
    	
    	Optional<User> optionalUser = repository.findById(user.getId());
    	
    	assertTrue(optionalUser.isPresent());
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou nula, deve retorna um False se o id nao existe no banco")
    public void findByTurmaIdUserNaoExistente() {
    	
    	List<User> listUser = repository.findByIdIn(notlistUserId);
    	assertThat(listUser).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdUserExistente() {
    	
    	List<User> listUser = repository.findByIdIn(listUserId);
    	assertThat(listUser).isNotEmpty();
    }
    
    @Test
    @DisplayName("Deve retorna um False se o email nao existe no banco")
    public void findByEmailUserNaoExistente() {
    	
    	User user = repository.findByEmail("Mateus@gmail.com");
    	assertThat(user).isNull();
    }
    
    @Test
    @DisplayName("Deve retorna um False se o email existe no banco")
    public void findByEmailUserExistente() {
    	
    	User user = repository.findByEmail("joao@gmail.com");
    	assertThat(user).isNotNull();
    }
}
