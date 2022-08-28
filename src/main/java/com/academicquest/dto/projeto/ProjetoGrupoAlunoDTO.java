package com.academicquest.dto.projeto;

import com.academicquest.enums.STATUS_PROJETO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoGrupoAlunoDTO {

    private String nomeProjeto;
    private String statusProjeto;
    private String notaProjeto;
    private String nomeMateria;
    private Long grupoId;
    private Long projetoId;
    private String nomeGrupo;

}
