package com.academicquest.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    private List<ChatDto> chats;

}
