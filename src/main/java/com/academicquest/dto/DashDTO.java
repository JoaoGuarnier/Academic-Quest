package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashDTO {
	
	private Integer totalEntregue = 0;
	private Integer totalPendente = 0;
	private Integer totalNaoEntregue = 0;
	private Integer totalCorrigida = 0;
	private Integer alunoId = 0;
	private Integer tarefaId = 0;
	private Integer naoRespondeId = 0;
}
