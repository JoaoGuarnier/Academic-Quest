package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaGrupoProjetoDTO {

    private String nomeTarefa;
    private Long tarefaGrupoId;
    private String dataEntrega;
    private String statusTarefa;

}
