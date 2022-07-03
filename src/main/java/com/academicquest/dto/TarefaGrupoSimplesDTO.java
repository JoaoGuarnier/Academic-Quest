package com.academicquest.dto;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaGrupoSimplesDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private String nomeGrupo;

    private Double nota;

    private LocalDateTime dataEntrega;

    private STATUS_TAREFA_GRUPO statusTarefaGrupo;

    public TarefaGrupoSimplesDTO (TarefaGrupo tarefaGrupo) {
        this.id = tarefaGrupo.getId();
        this.nomeGrupo = tarefaGrupo.getGrupo().getNome();
        this.nota = tarefaGrupo.getNota();
        this.dataEntrega = tarefaGrupo.getDataEntrega();
        this.statusTarefaGrupo = tarefaGrupo.getStatusTarefaGrupo();
    }

}
