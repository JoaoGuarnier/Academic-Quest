package com.academicquest.repository;

import static com.academicquest.components.UtilMock.Tarefa_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosTest.createTarefa;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.model.Tarefa;

@DataJpaTest
public class TarefaRepositoryTest {

	@Autowired
	private TarefaRepository repository;

	@Test
    @DisplayName("Deve salvar um Tarefa.")
    public void saveTarefa() {
    	
    	Tarefa tarefa = createTarefa();
    	
    	repository.save(tarefa);

        assertNotNull(tarefa);
    }
    
    @Test
    @DisplayName("Deve deleta uma Tarefa quando tiver no banco")
    public void deleteTarefaExistente() {
    	
        repository.save(createTarefa());

    	repository.deleteById(1l);
    	
        Optional<Tarefa> result = repository.findById(1l);
        
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaTarefaNaoExistente() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(Tarefa_ID_NAO_EXISTE);
        });
    }
    
    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdTarefaExistente() {
    	
    	Tarefa tarefa = repository.save(createTarefa());
    	
        Optional<Tarefa> optionalMateria = repository.findById(tarefa.getId());
        
        assertTrue(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdTarefaNaoExistente() {
    	
        Optional<Tarefa> optionalMateria = repository.findById(Tarefa_ID_NAO_EXISTE);
        assertFalse(optionalMateria.isPresent());
    }
}