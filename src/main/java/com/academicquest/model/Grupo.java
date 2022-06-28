package com.academicquest.model;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_grupo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
    @ManyToMany()
    @JoinTable(name = "tb_grupo_user",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> alunos;
	
    @ManyToOne()
    @JoinColumn(name = "materia_id")
	private Materia materia;
    
    @ManyToOne()
    @JoinColumn(name = "user_lider_id")
    private User alunoLider;

    @OneToOne(mappedBy = "grupo")
    private ProjetoGrupo projetoGrupo;

}
