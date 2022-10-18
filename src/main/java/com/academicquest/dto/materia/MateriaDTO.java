package com.academicquest.dto.materia;

import java.io.Serializable;

import com.academicquest.dto.user.UserDTO;
import com.academicquest.model.Materia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable{

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
