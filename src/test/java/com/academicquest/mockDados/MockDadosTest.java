package com.academicquest.mockDados;

import java.util.ArrayList;
import java.util.List;
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
		
		Projeto projeto = new Projeto();

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
}
