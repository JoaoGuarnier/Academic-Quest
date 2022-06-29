package com.academicquest.dto;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaGrupoDTO implements Serializable{

    private Long id;


    private String nomeTarefa;

    private String nomeGrupo;

    private Double nota;

    private LocalDateTime dataEntrega;

    private STATUS_TAREFA_GRUPO statusTarefaGrupo = STATUS_TAREFA_GRUPO.PENDENTE;

    private UploadDTO upload;

    private String consideracoes;

}
