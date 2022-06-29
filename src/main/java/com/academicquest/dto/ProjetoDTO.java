package com.academicquest.dto;

import com.academicquest.enums.STATUS_PROJETO;
import com.academicquest.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO implements Serializable{

    private Long id;
    private String nome;
    private String descricao;
    private STATUS_PROJETO status;
    private String materia;

    public ProjetoDTO(Projeto projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.status = projeto.getStatus();
        this.materia = projeto.getMateria().getNome();
    }

}
