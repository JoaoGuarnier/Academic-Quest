package com.academicquest.mockDados;

import static com.academicquest.mockDados.MockDadosTest.createChat;
import static com.academicquest.mockDados.MockDadosTest.createGrupo;
import static com.academicquest.mockDados.MockDadosTest.createMateria;
import static com.academicquest.mockDados.MockDadosTest.createProjeto;
import static com.academicquest.mockDados.MockDadosTest.createRole;
import static com.academicquest.mockDados.MockDadosTest.createTarefa;
import static com.academicquest.mockDados.MockDadosTest.createTurma;
import static com.academicquest.mockDados.MockDadosTest.createUser;

import java.util.ArrayList;
import java.util.List;

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
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
		
		return new GrupoDTO(createGrupo());
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
		
		return new GrupoPostDTO(createGrupo().getNome(), alunosId, createGrupo().getAlunoLider().getId(), createGrupo().getMateria().getId());
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
		
		return new ProjetoPostDTO(createProjeto().getNome(), createProjeto().getDescricao(), createProjeto().getMateria().getId());
	}
	
	public static RoleDTO createRoleDTO() {
		
		return new RoleDTO(createRole());
	}
	
	public static TurmaDTO createTurmaDTO() {
		
		return new TurmaDTO(createTurma());
	}
	
	public static TarefaPostDTO createTarefaPostDTO() {
		
		return new TarefaPostDTO(createTarefa().getTitulo(), createTarefa().getDescricao(), createTarefa().getProjeto().getId());
	}
	
	public static MateriaDTO createMateriaDTO() {
		
		return new MateriaDTO(createMateria());
	}
	
	public static ChatDto createChatDTO() {
		
		return new ChatDto(createChat());
	}
	
	public static ChatPostDto createChatPostDTO() {
		
		return new ChatPostDto(createChat().getMensagem(), createChat().getDataHoras(), createChat().getUser());
	}
}
