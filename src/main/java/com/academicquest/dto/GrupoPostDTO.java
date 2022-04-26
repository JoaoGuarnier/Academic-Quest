package com.academicquest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoPostDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private List<Long> alunosId;
	
	private Long alunoLiderId;
	
	private Long materiaId;

}
