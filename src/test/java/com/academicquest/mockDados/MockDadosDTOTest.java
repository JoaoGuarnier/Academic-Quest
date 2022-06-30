package com.academicquest.mockDados;

import static com.academicquest.mockDados.MockDadosTest.createGrupo;
import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static com.academicquest.mockDados.MockDadosTest.createProjeto;
import static com.academicquest.mockDados.MockDadosTest.createRole;
import static com.academicquest.mockDados.MockDadosTest.createTurma;
import static com.academicquest.mockDados.MockDadosTest.createUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.MateriaDTO;
import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.dto.RoleDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.dto.TurmaDTO;
import com.academicquest.dto.UserDTO;

public class MockDadosDTOTest {

	public static GrupoDTO createGrupoDTO() {
		
		UserDTO userDTO = new UserDTO();
		
		List<UserDTO> listaAlunos = new ArrayList<>();
		listaAlunos.add(userDTO);
		
		
		return new GrupoDTO(1L, "MALUCATIONS", listaAlunos, 1L, 1L);
	}
	
	public static GrupoMateriaDTO createGrupoMateriaDTO() {
		
		return new GrupoMateriaDTO(createGrupo());
	}
	
	public static UserDTO createUserDTO() {
		
		return new UserDTO(createUser());
	}
	
	public static GrupoPostDTO createGrupoPostDTO() {
		
		List<Long> alunosId = new ArrayList<Long>();
		alunosId.add(1L);
		
		return new GrupoPostDTO("MALUCATIONS", alunosId, 1L, 1L);
	}
	
	public static ProjetoDTO createProjetoDTO() {
		
		return new ProjetoDTO(MockDadosTest.createProjeto());
	}
	
	public static GrupoUpdateDTO createGrupoUpdateDTO() {
		
		List<Long> alunosId = new ArrayList<Long>();
		alunosId.add(1L);
		
		return new GrupoUpdateDTO(createGrupo().getNome(), createGrupo().getAlunoLider().getId(), alunosId);
	}
	
	public static ProjetoPostDTO createProjetoPostDTO() {
		return new ProjetoPostDTO(createProjeto().getNome(), createProjeto().getDescricao(), 1L);
	}
	
	public static RoleDTO createRoleDTO() {
		
		return new RoleDTO(createRole());
	}
	
	public static TurmaDTO createTurmaDTO() {
		
		return new TurmaDTO(createTurma());
	}
	
	public static TarefaPostDTO createTarefaPostDTO() {
		MultipartFile multipartFileToSend = new MockMultipartFile("aa", new byte[] {});
		
		return new TarefaPostDTO(1L, "Banco de dados", "Noturno", "aa", multipartFileToSend, 1L);
	}
	
	public static MateriaDTO createMateriaDTO() {
		
		return new MateriaDTO(createMateria());
	}
}
