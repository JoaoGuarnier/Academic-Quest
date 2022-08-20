package com.academicquest.dto.dashboard;

import java.io.Serializable;
import java.util.List;

import com.academicquest.enums.STATUS_TAREFA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardTarefaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nomeTarefa;
	private String dataEntrega;
	private STATUS_TAREFA status;
	private List<DashBoardTarefaGrupoDTO> tarefasGrupos;
}
