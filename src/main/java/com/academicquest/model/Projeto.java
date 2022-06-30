package com.academicquest.model;

import com.academicquest.enums.STATUS_PROJETO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;

@Entity
@Table(name = "tb_projeto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private STATUS_PROJETO status;

    @ManyToOne()
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @OneToMany(mappedBy = "projeto")
    private List<Tarefa> listaTarefas;

}
