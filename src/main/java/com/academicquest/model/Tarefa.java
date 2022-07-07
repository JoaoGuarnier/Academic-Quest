package com.academicquest.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_tarefa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private LocalDate dataEntrega;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_id", referencedColumnName = "id")
    private Upload upload;

    @ManyToOne(fetch = FetchType.EAGER)
    private Projeto projeto;

    @OneToOne(mappedBy = "grupo")
    private TarefaGrupo tarefaGrupo;

}
