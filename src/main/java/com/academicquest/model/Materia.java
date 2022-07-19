package com.academicquest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_materia")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Materia {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
    @ManyToOne()
    @JoinColumn(name = "professor_id")
	private User professor;
	
    @ManyToOne()
    @JoinColumn(name = "turma_id")
	private Turma turma;
}
