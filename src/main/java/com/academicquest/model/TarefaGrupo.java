package com.academicquest.model;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tarefa_grupo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Grupo grupo;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tarefa_id")
    private Tarefa tarefa;

    private Double nota;

    private LocalDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private STATUS_TAREFA_GRUPO statusTarefaGrupo = STATUS_TAREFA_GRUPO.PENDENTE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_id", referencedColumnName = "id")
    private Upload upload;

    private String consideracoes;
}
