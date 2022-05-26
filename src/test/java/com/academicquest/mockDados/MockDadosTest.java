package com.academicquest.mockDados;

import java.util.ArrayList;
import java.util.List;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.Projeto;
import com.academicquest.model.Role;
import com.academicquest.model.Tarefa;
import com.academicquest.model.Turma;
import com.academicquest.model.User;

public class MockDadosTest {

	public static Grupo createGrupo() {

		List<Tarefa> tarefaList = new ArrayList<>();

		tarefaList.add(createTarefa());

		List<User> userList = new ArrayList<>();

		userList.add(createUser());

		Grupo grupo = new Grupo(1l, "Codao", userList, createMateria(), createUser());

		return grupo;
	}
	

	public static Projeto createProjeto() {

		List<Tarefa> tarefaList = new ArrayList<>();

		tarefaList.add(createTarefa());

		List<User> userList = new ArrayList<>();

		userList.add(createUser());

		Projeto projeto = new Projeto(1L, "Codao", "Phone", STATUS_PROJETO.CONCLUIDO, createMateria(), tarefaList);

		return projeto;
	}

	public static Tarefa createTarefa() {
		
		Projeto projeto = new Projeto(1L, "Codao", "Phone", STATUS_PROJETO.CONCLUIDO, createMateria(), new ArrayList<Tarefa>());

		return new Tarefa(1L, "Banco de dados", "Noturno", projeto);
	}

	public static Materia createMateria() {

		Materia materia = new Materia(1L, "Phone", createUser(), createTurma());

		return materia;
	}

	public static User createUser() {

		List<Role> rolesList = new ArrayList<>();

		rolesList.add(createRole());
		rolesList.add(createRole());
		rolesList.add(createRole());

		return new User(1L, "Codao", "Alves", 1, "leon.codao@gmail.com", "senha123", 1L, rolesList);
	}

	public static Role createRole() {

		Role role = new Role(1L, "token");

		return role;
	}

	public static Turma createTurma() {

		return new Turma(1L, "token", 1, "Nortuno");
	}
	
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
		
		return new GrupoPostDTO("Codao", alunosId, 1L, 1L);
	}
	
	
	public static GrupoUpdateDTO createGrupoUpdateDTO() {
		
		List<Long> alunosId = new ArrayList<Long>();
		alunosId.add(1L);
		
		return new GrupoUpdateDTO("Codao", 1L, alunosId);
	}
}
