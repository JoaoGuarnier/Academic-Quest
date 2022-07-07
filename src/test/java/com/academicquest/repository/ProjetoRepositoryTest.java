package com.academicquest.repository;

import static com.academicquest.components.UtilMock.PROJETO_ID;
import static com.academicquest.components.UtilMock.PROJETO_ID_NAO_EXISTE;
import static com.academicquest.mockDados.MockDadosTest.createProjeto;
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

import com.academicquest.model.Projeto;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ProjetoRepositoryTest {

	@Autowired
	private ProjetoRepository repository;

    @BeforeEach
    public void setUpProjeto() throws Exception  {
        repository.save(createProjeto());
    }
    
    @Test
    @Order(1)
    @DisplayName("Deve deleta uma Projeto quando tiver no banco")
    public void deleteExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<Projeto> projeto = repository.findById(1l);
    	
    	assertFalse(projeto.isPresent());
    }
    
    @Test
    @Order(2)
    @DisplayName("Deve retorna um False se o id, nao existe no banco")
    public void findByIdNaoExistir() {
    	
        Optional<Projeto> optionalProjeto = repository.findById(PROJETO_ID_NAO_EXISTE);
        assertFalse(optionalProjeto.isPresent());
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve lanca uma exception quando nao existir o resultado no banco")
    public void deletaNaoExistente() {
    	
    	assertThrows(EmptyResultDataAccessException.class,() -> {
    		repository.deleteById(PROJETO_ID_NAO_EXISTE);
    	});
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve salvar um Projeto.")
    public void saveProjeto() {
    	
    	Projeto projeto = createProjeto();
    	
    	projeto = repository.save(projeto);

        assertNotNull(projeto);
    }

    @Test
    @Order(5)
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir() {
    	
    	Projeto projeto = repository.save(createProjeto());
    	
    	Optional<Projeto> optionalProjeto = repository.findById(projeto.getId());
    	
    	assertTrue(optionalProjeto.isPresent());
    }
    
    @Test
    @Order(6)
    @DisplayName("Se a lista estiver vazia ou nula, deve retorna um False e se o id nao existe no banco")
    public void findByTurmaIdNaoExistir() {
    	
    	List<Projeto> optionalProjeto = repository.findByMateriaId(PROJETO_ID_NAO_EXISTE);
    	assertThat(optionalProjeto).isNullOrEmpty();
    }
    
    @Test
    @Order(7)
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Projeto> optionalProjeto = repository.findByMateriaId(PROJETO_ID);
    	assertThat(optionalProjeto).isNotEmpty();
    }
}
