package com.academicquest.dto;

import com.academicquest.model.ProjetoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoGrupoDTO {

    private Long id;

    private Double nota;

    private String projeto;

    private String grupo;

    public ProjetoGrupoDTO(ProjetoGrupo projetoGrupo) {
        this.id = projetoGrupo.getId();
        this.nota = projetoGrupo.getNota();
        this.projeto = projetoGrupo.getProjeto().getNome();
        this.grupo = projetoGrupo.getGrupo().getNome();
    }

}
