package com.academicquest.dto;

import java.io.Serializable;

import com.academicquest.model.Turma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String curso;
	
	private Integer semestre;
	
	private String complemento;
	
	public TurmaDTO(Turma turma) {
		this.id = turma.getId();
		this.curso = turma.getCurso();
		this.semestre = turma.getSemestre();
		this.complemento = turma.getComplemento();
	}

}
