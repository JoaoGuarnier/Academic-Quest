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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Materia;

@DataJpaTest
public class MateriaRepositoryTest {

	@Autowired
	private MateriaRepository repository;
	
    private long existingId;
    private long nonExistingId;

    @BeforeEach
    public void setUp() {
        existingId = 1;
        nonExistingId = 999;
    }
    
    @Test
    @DisplayName("Deve salvar um Materia.")
    public void saveMateria() {
    	
    	Materia materia = MockDadosTest.createMateria();
    	
    	materia = repository.save(materia);

        assertNotNull(materia);
    }
    
    @Test
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteExistir() {
    	
    	repository.deleteById(existingId);
    	
        Optional<Materia> result = repository.findById(1l);
        
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
    	
        Optional<Materia> optionalMateria = repository.findById(existingId);
        assertTrue(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, existe no banco")
    public void findByIdNotExistir() {
    	
        Optional<Materia> optionalMateria = repository.findById(nonExistingId);
        assertFalse(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void findByTurmaIdNotExistir() {
    	
    	List<Materia> optionalMateria = repository.findByTurmaId(nonExistingId);
    	assertThat(optionalMateria).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Materia> optionalMateria = repository.findByTurmaId(existingId);
    	assertThat(optionalMateria).isNotEmpty();
    }
}
