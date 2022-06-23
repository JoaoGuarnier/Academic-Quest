package com.academicquest.dto;

import com.academicquest.model.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;

    private String nome;

    private String descricao;

    private String nomeProjeto;

    private String nomeArquivo;

    private byte[] arquivo;

    public TarefaDTO(Tarefa tarefa, String nomeArquivoUpload) {

        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.nomeProjeto = tarefa.getProjeto().getNome();
        this.nomeArquivo = nomeArquivoUpload;

    }

    public TarefaDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.arquivo = tarefa.getArquivoUpload();
    }
}
