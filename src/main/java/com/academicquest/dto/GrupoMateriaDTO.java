package com.academicquest.dto;

import java.io.Serializable;

import com.academicquest.model.Grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoMateriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	public GrupoMateriaDTO(Grupo grupo) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
	}
}
