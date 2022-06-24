package com.academicquest.repository;

import static com.academicquest.components.UtilMock.Turma_ID;
import static com.academicquest.components.UtilMock.Turma_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosTest.createTurma;
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

import com.academicquest.model.Turma;

@DataJpaTest
public class TurmaRepositoryTest {

	@Autowired
	private TurmaRepository repository;
	
    @Test
    @DisplayName("Deve salvar um Turma.")
    public void saveTurma() {
    	
    	Turma turma = createTurma();
    	
    	turma = repository.save(turma);

        assertNotNull(turma);
    }
    
    @Test
    @DisplayName("Deve deleta uma Turma quando tiver no banco")
    public void deleteTurmaExistente() {
    	
    	repository.deleteById(Turma_ID);
    	
        Optional<Turma> result = repository.findById(1l);
        
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaTurmaNaoExistente() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(Turma_ID_NAO_EXISTE);
        });
    }
    
    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdTurmaExistir() {
    	
        Optional<Turma> optionalTurma = repository.findById(Turma_ID);
        assertTrue(optionalTurma.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdTurmaNotExistir() {
    	
        Optional<Turma> optionalTurma = repository.findById(Turma_ID_NAO_EXISTE);
        assertFalse(optionalTurma.isPresent());
    }
}