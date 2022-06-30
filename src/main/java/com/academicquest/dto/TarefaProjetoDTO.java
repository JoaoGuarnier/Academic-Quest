package com.academicquest.dto;

import com.academicquest.model.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaProjetoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private String nome;

    private String descricao;

    private LocalDate dataEntrega;

    public TarefaProjetoDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.dataEntrega = tarefa.getDataEntrega();
    }

}
