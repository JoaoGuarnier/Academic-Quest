package com.academicquest.dto.materia;

import java.io.Serializable;
import java.util.List;

import com.academicquest.dto.user.UserDTO;
import com.academicquest.model.Materia;
import com.academicquest.model.Turma;

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
		this.turmas = List.of(materia.getTurma()); 
	}
	
	private Long id;
	
	private String nome;
	
	private UserDTO professor;
	
	private List<Turma> turmas;
}
