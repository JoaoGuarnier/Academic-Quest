package com.academicquest.dto;

import com.academicquest.model.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private String nome;

    private String descricao;

    private LocalDate dataEntrega;

    private String nomeProjeto;

    private String nomeArquivo;

    private byte[] upload;

    private String formato;

    public TarefaDTO(Tarefa tarefa, String nomeArquivoUpload) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.nomeProjeto = tarefa.getProjeto().getNome();
        this.nomeArquivo = nomeArquivoUpload;
        this.upload = tarefa.getUpload().getArquivoUpload();
        this.formato = tarefa.getUpload().getFormato();
        this.dataEntrega = tarefa.getDataEntrega();
    }

    public TarefaDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.nomeProjeto = tarefa.getProjeto().getNome();
        this.nomeArquivo = tarefa.getUpload().getTitulo();
        this.upload = tarefa.getUpload().getArquivoUpload();
        this.dataEntrega = tarefa.getDataEntrega();
        this.formato = tarefa.getUpload().getFormato();
    }

}
