package com.academicquest.mockDados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Materia;
import com.academicquest.model.Projeto;
import com.academicquest.model.ProjetoGrupo;
import com.academicquest.model.Role;
import com.academicquest.model.Tarefa;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.Turma;
import com.academicquest.model.Upload;
import com.academicquest.model.User;

public class MockDadosTest {
	
	public static ProjetoGrupo createProjetoGrupo() {

		ProjetoGrupo projetoGrupo = new ProjetoGrupo(1L, 1.1, createProjeto(), createGrupo());

		return projetoGrupo;
	}

	public static Grupo createGrupo() {

		List<User> userList = new ArrayList<>();
		ProjetoGrupo projetoGrupo = new ProjetoGrupo();
		

		userList.add(createUser());

		Grupo grupo = new Grupo(1L, "MALUCATIONS", userList, createMateria(), createUser(), projetoGrupo);

		return grupo;
	}

	public static Projeto createProjeto() {

		List<Tarefa> tarefaList = new ArrayList<>();

		tarefaList.add(createTarefa());

		List<User> userList = new ArrayList<>();

		userList.add(createUser());
		
		Projeto projeto = new Projeto(1L, "Leon", "Noturno", STATUS_PROJETO.CONCLUIDO, createMateria(), tarefaList);

		return projeto;
	}

	public static Tarefa createTarefa() {
		
		Projeto projeto = new Projeto();
		TarefaGrupo tarefaGrupo = new TarefaGrupo();
		Upload upload = new Upload();

		return new Tarefa(1L, "Banco de dados", "Noturno", LocalDate.now(), upload, projeto, tarefaGrupo);
	}
	
	public static Upload createUpload() {
		
		return new Upload(1L, "Banco de dados", "png",  new byte[] {});
	}
	
	public static TarefaGrupo createTarefaGrupo() {
		
		Grupo grupo = new Grupo();
		Tarefa tarefa = new Tarefa();
		
		Upload upload = new Upload();
		
		return new TarefaGrupo(1L, grupo, tarefa, 1.1d, LocalDateTime.now(), STATUS_TAREFA_GRUPO.PENDENTE, upload, "Finalizamos");
	}

	public static Materia createMateria() {

		Materia materia = new Materia(1L, "Leon", createUser(), createTurma());

		return materia;
	}

	public static User createUser() {

		List<Role> rolesList = new ArrayList<>();
		
		rolesList.add(createRole());
		rolesList.add(createRole());
		rolesList.add(createRole());

		return new User(1L, "Joao", "Alves", 1, "leon.Joao@gmail.com", "adivinha123", 1L, rolesList);
	}

	public static Role createRole() {

		Role role = new Role(1L, "token");

		return role;
	}

	public static Turma createTurma() {

		return new Turma(1L, "token", 1, "Nortuno");
	}
	
	public static Chat createChat() {
		
		return new Chat(1L, "Bom dia!", LocalDateTime.now(), createUser());
	}
}
