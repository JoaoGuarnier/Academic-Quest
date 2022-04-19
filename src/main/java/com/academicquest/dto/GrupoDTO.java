package com.academicquest.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.academicquest.model.Grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private List<UserDTO> alunos;
	
	private Long alunoLiderId;
	
	private Long materiaId;
	
	public GrupoDTO(Grupo grupo) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
		this.alunoLiderId = grupo.getIdUserLider();
		this.materiaId = grupo.getMateria().getId();
		
		List<UserDTO> userDTOsList = grupo.getAlunos().stream().map(UserDTO::new).collect(Collectors.toList());
		this.alunos = userDTOsList;
	}

}
