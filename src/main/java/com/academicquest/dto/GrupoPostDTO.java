package com.academicquest.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoPostDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="O nome nao pode ser ser nulo, tem que ser preenchido")
	private String nome;
	
	@NotNull(message="O id da lista nao pode ser nulo, tem que ser preenchido")
	private List<Long> alunosId;
	
	@NotNull(message="o id da aluno lider nao pode ser nulo, tem que ser preenchido")
	private Long alunoLiderId;
	
	@NotNull(message="o id da materia nao pode ser nulo, tem que ser preenchido")
	private Long materiaId;
}
