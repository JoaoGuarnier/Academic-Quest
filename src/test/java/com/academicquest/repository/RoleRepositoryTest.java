package com.academicquest.repository;

import static com.academicquest.mockDados.MockDadosTest.createRole;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.model.Role;

@DataJpaTest
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repository;
	
    private long roleId;
    private long notRoleId;

    @BeforeEach
    public void setUpRole() {
        roleId = 1;
        notRoleId = 999;
    }
    
    @Test
    @DisplayName("Deve salvar um Role.")
    public void saveRole() {
    	
    	Role role = createRole();
    	
    	role = repository.save(role);

        assertNotNull(role);
    }
    
    @Test
    @DisplayName("Deve deleta uma Role quando tiver no banco")
    public void deleteRoleExistir() {
    	
    	repository.deleteById(roleId);
    	
        Optional<Role> result = repository.findById(1l);
        
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaRoleNaoExistente() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(notRoleId);
        });
    }
    
    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdRoleExistir() {
    	
        Optional<Role> optionalRole = repository.findById(roleId);
        assertTrue(optionalRole.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdRoleNaoExistente() {
    	
        Optional<Role> optionalRole = repository.findById(notRoleId);
        assertFalse(optionalRole.isPresent());
    }
}