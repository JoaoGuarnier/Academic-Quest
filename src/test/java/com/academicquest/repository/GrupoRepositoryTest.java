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
	
    private Long grupoId;
    private Long notGrupoId;

    @BeforeEach
    public void setUpGrupo() throws Exception  {
    	grupoId = 1L;
    	notGrupoId = 999L;
    }
    
    @Test
    @DisplayName("Deve deleta uma Grupo quando tiver no banco")
    public void deleteGrupoExistir() {
    	
    	repository.deleteById(1L);
    	
    	Optional<Grupo> grupo = repository.findById(1l);
    	
    	assertFalse(grupo.isPresent());
    }
    
    @Test
    @DisplayName("Deve salvar um Grupo.")
    public void saveGrupo() {
    	
    	Grupo grupo = MockDadosTest.createGrupo();
    	
    	grupo = repository.save(grupo);

        assertNotNull(grupo);
    }
    
    @Test
    @DisplayName("Deve lanca uma exception quando o valor nao existir o resultado no banco")
    public void deletaGrupoNaoExiste() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(notGrupoId);
        });
    }

    @Test
    @DisplayName("Deve retorna um False se o id, nao existe no banco")
    public void testarFindByIdNaoExistente() {
    	
        Optional<Grupo> optionalGrupo = repository.findById(notGrupoId);
        assertFalse(optionalGrupo.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    public void testarFindByIdExistente() {
    	
    	Grupo grupo = repository.save(MockDadosTest.createGrupo());
    	
    	Optional<Grupo> optionalUser = repository.findById(grupo.getId());
    	
    	assertTrue(optionalUser.isPresent());
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou nula deve retorna um False, e se o id nao existe no banco")
    public void findByTurmaIdNaoExistir() {
    	
    	List<Grupo> listGrupo = repository.findByMateriaId(notGrupoId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void findByTurmaIdExistir() {
    	
    	List<Grupo> listGrupo = repository.findByMateriaId(grupoId);
    	assertThat(listGrupo).isNotEmpty();
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou nula, deve retorna um False se o id nao existe no banco")
    public void buscarAlunosMateriaNaoExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosMateria(notGrupoId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void buscaAlunosMateriaExistente() {
    	
    	List<Long> listGrupo = repository.buscaAlunosMateria(grupoId);
    	assertThat(listGrupo).isNotEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um false, e se o id nao existe no banco")
    public void buscaAlunosComGrupoMateriaNaoExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosComGrupoMateria(notGrupoId);
    	assertThat(listGrupo).isNullOrEmpty();
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento retorna um true, e se o id existe no banco")
    public void buscaAlunosComGrupoMateriaExistir() {
    	
    	List<Long> listGrupo = repository.buscaAlunosComGrupoMateria(grupoId);
    	assertThat(listGrupo).isNotEmpty();
    }
}
