package com.academicquest.repository;

import static com.academicquest.components.UtilMock.Materia_ID;
import static com.academicquest.components.UtilMock.Materia_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.model.Materia;

@DataJpaTest
public class MateriaRepositoryTest {

	@Autowired
	private MateriaRepository repository;
    
    @Test
    @DisplayName("Deve salvar um Materia.")
    public void saveMateria() {
    	
    	Materia materia = createMateria();
    	
    	materia = repository.save(materia);

        assertNotNull(materia);
    }
    
    @Test
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteMateriaExistir() {
    	
    	repository.deleteById(Materia_ID);
    	
        Optional<Materia> result = repository.findById(1l);
        
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaMateriaNaoExistente() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(Materia_ID_NAO_EXISTE);
        });
    }
    
    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir() {
    	
        Optional<Materia> optionalMateria = repository.findById(Materia_ID);
        assertTrue(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um False se o id, nao existe no banco")
    public void findByIdNaoExistir() {
    	
        Optional<Materia> optionalMateria = repository.findById(Materia_ID_NAO_EXISTE);
        assertFalse(optionalMateria.isPresent());
    }

    @Test
    @DisplayName("Se a lista estiver vazia ou nula, deve retorna um False")
    public void findByTurmaIdNaoExistir() {
    	
    	List<Materia> optionalMateria = repository.findByTurmaId(Materia_ID_NAO_EXISTE);
    	assertThat(optionalMateria).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento, retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Materia> optionalMateria = repository.findByTurmaId(Materia_ID);
    	assertThat(optionalMateria).isNotEmpty();
    }
}
