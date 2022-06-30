package com.academicquest.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoPostDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
    private String descricao;
    private Long materiaId;

}
