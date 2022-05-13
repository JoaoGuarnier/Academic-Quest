package com.academicquest.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.academicquest.model.Materia;
import com.academicquest.model.Turma;
import com.academicquest.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public MateriaDTO(Materia materia) {
		this.id = materia.getId();
		this.nome = materia.getNome();
		this.professor = new UserDTO(materia.getProfessor());
	}
	
	private Long id;
	
	private String nome;
	
	private UserDTO professor;


}
