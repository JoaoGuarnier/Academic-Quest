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
import com.academicquest.model.Grupo;

@DataJpaTest
public class GrupoRepositoryTest {

	@Autowired
	private GrupoRepository repository;
	
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    public void setUp() throws Exception  {
    	existingId = 1L;
    	nonExistingId = 999L;
    }
    
    @Test
    @DisplayName("Deve deleta uma Materia quando tiver no banco")
    public void deleteExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<Grupo> grupo = repository.findById(1l);
    	
    	assertFalse(grupo.isPresent());
    }
    
    @Test
    @DisplayName("Deve salvar um User.")
    public void saveUser() {
    	
    	Grupo grupo = MockDadosTest.createGrupo();
    	
    	grupo = repository.save(grupo);

        assertNotNull(grupo);
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
    	
        Optional<Grupo> optionalGrupo = repository.findById(nonExistingId);
        assertFalse(optionalGrupo.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void findByIdExistir3() {
    	
    	Grupo grupo = repository.save(MockDadosTest.createGrupo());
    	
    	Optional<Grupo> optionalUser = repository.findById(grupo.getId());
    	
    	assertTrue(optionalUser.isPresent());
    }
    
    
    
    @Test
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void findByTurmaIdNotExistir() {
    	
    	List<Grupo> listGrupo = repository.findByMateriaId(nonExistingId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Grupo> listGrupo = repository.findByMateriaId(existingId);
    	assertThat(listGrupo).isNotEmpty();
    }
 
    
    @Test
    @DisplayName("Se a lista estiver vazia ou null, deve retorna um False se o id nao existe no banco")
    public void buscaAlunosMateriaNotExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosMateria(nonExistingId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void buscaAlunosMateriaExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosMateria(existingId);
    	assertThat(listGrupo).isNotEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void buscaAlunosComGrupoMateriaNotExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosComGrupoMateria(nonExistingId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void buscaAlunosComGrupoMateriaExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosComGrupoMateria(existingId);
    	assertThat(listGrupo).isNotEmpty();
    }
}
