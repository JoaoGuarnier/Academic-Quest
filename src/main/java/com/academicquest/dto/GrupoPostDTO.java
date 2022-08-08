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

	private String nome;

	private List<Long> listaAlunosId;

	private Long alunoLiderId;

	private Long materiaId;
}
