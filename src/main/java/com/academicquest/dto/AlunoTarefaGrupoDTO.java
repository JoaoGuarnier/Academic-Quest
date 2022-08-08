package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoTarefaGrupoDTO {

    private String nomeMateria;
    private String nomeGrupo;
    private String nomeAtividade;
    private Long idTarefaGrupo;
    private String nomeProjeto;
    private String dataEntregaAtividade;

}
