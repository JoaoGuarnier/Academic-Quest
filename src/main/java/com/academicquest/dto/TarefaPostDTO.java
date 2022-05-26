package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaPostDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String titulo;
    private String descricao;
    private Long idProjeto;
}
