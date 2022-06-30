package com.academicquest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_projeto_grupo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota = 0.0;

    @ManyToOne
    private Projeto projeto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

}
