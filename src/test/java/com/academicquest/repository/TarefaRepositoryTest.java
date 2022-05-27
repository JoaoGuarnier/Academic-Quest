package com.academicquest.repository;

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

import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Tarefa;

@DataJpaTest
public class TarefaRepositoryTest {

	@Autowired
	private TarefaRepository repository;
	
    private long nonExistingId;
    
    @BeforeEach
    public void setUp() {
        nonExistingId = 999;
    }
    
    @Test
    @DisplayName("Deve salvar um Tarefa.")
    public void saveMateria() {
    	
    	Tarefa tarefa = MockDadosTest.createTarefa();
    	
    	repository.save(tarefa);

        assertNotNull(tarefa);
    }
    
    @Test
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteExistir() {
    	
        repository.save(MockDadosTest.createTarefa());

    	repository.deleteById(1L);
    	
        Optional<Tarefa> result = repository.findById(1l);
        
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve lanca uma exception quando nao existir  o resultado no banco")
    public void deletaELancaException() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(nonExistingId);
        });
    }
    
    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir() {
    	
    	Tarefa tarefa = repository.save(MockDadosTest.createTarefa());
    	
        Optional<Tarefa> optionalMateria = repository.findById(tarefa.getId());
        
        assertTrue(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdNotExistir() {
    	
        Optional<Tarefa> optionalMateria = repository.findById(nonExistingId);
        assertFalse(optionalMateria.isPresent());
    }
}