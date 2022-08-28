package com.academicquest.dto.tarefaGrupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaGrupoPutDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Double nota;

    private String consideracoes;

}
