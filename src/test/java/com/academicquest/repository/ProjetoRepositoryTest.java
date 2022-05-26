package com.academicquest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Projeto;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ProjetoRepositoryTest {

	@Autowired
	private ProjetoRepository repository;
	
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    public void setUp() throws Exception  {
        existingId = 1L;
        nonExistingId = 999L;
        repository.save(MockDadosTest.createProjeto());
    }
    
    @Test
    @Order(0)
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<Projeto> projeto = repository.findById(1l);
    	
    	assertFalse(projeto.isPresent());
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve salvar um Projeto.")
    public void saveProjeto() {
    	
    	Projeto projeto = MockDadosTest.createProjeto();
    	
    	projeto = repository.save(projeto);

        assertNotNull(projeto);
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve lanca uma exception quando nao existir  o resultado no banco")
    public void deletaELancaException() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(nonExistingId);
        });
    }
    
  

    @Test
    @Order(2)
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdNotExistir() {
    	
        Optional<Projeto> optionalProjeto = repository.findById(nonExistingId);
        assertFalse(optionalProjeto.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir3() {
    	
    	Projeto projeto = repository.save(MockDadosTest.createProjeto());
    	
    	Optional<Projeto> optionalProjeto = repository.findById(projeto.getId());
    	
    	assertTrue(optionalProjeto.isPresent());
    }
    
    @Test
    @Order(6)
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void findByTurmaIdNotExistir() {
    	
    	List<Projeto> optionalMateria = repository.findyByMateriaId(nonExistingId);
    	assertThat(optionalMateria).isNullOrEmpty();
    }
    
    @Test
    @Order(7)
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Projeto> optionalMateria = repository.findyByMateriaId(existingId);
    	assertThat(optionalMateria).isNotEmpty();
    }
}
