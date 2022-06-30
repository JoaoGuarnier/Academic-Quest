package com.academicquest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_nota_aluno_projeto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaAlunoProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota;

    @ManyToOne
    private User aluno;

    @ManyToOne
    private Projeto projeto;

}
