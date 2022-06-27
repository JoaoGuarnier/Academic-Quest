package com.academicquest.dto;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Tarefa;
import com.academicquest.model.Upload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaGrupoDTO {

    private Long id;


    private String nomeTarefa;

    private String nomeGrupo;

    private Double nota;

    private LocalDateTime dataEntrega;

    private STATUS_TAREFA_GRUPO statusTarefaGrupo = STATUS_TAREFA_GRUPO.PENDENTE;

    private UploadDTO upload;

    private String consideracoes;

}
