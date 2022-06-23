package com.academicquest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_tarefa")
@Data
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
}
