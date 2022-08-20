package com.academicquest.dto.dashboard;

import java.io.Serializable;
import java.util.List;

import com.academicquest.enums.STATUS_PROJETO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardResultDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long projetoId;
	private String nomeProjeto;
	private STATUS_PROJETO statusProjeto;
	private List<DashBoardTarefaDTO> tarefas;
}
