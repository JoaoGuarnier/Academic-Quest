package com.academicquest.dto.tarefaGrupo;

import com.academicquest.dto.chat.ChatDTO;
import com.academicquest.dto.upload.UploadDTO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaGrupoDetalhesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nomeTarefa;

    private Long idTarefa;

    private String nomeGrupo;

    private Long idProjeto;

    private Double nota;

    private LocalDateTime dataEntrega;

    private STATUS_TAREFA_GRUPO statusTarefaGrupo;

    private String consideracoes;

    public TarefaGrupoDetalhesDto(TarefaGrupo tarefaGrupo) {
        this.id = tarefaGrupo.getId();
        this.nomeTarefa = tarefaGrupo.getTarefa().getNome();
        this.idTarefa = tarefaGrupo.getTarefa().getId();
        this.nomeGrupo = tarefaGrupo.getGrupo().getNome();
        this.idProjeto = tarefaGrupo.getTarefa().getProjeto().getId();
        this.nota = tarefaGrupo.getNota();
        this.dataEntrega = tarefaGrupo.getDataEntrega();
        this.statusTarefaGrupo = tarefaGrupo.getStatusTarefaGrupo();
        this.consideracoes = tarefaGrupo.getConsideracoes();
    }
}
