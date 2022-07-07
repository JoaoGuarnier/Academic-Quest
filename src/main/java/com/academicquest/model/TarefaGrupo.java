package com.academicquest.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.academicquest.enums.STATUS_TAREFA_GRUPO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotFound(action = NotFoundAction.IGNORE) 
    private Tarefa tarefa;

    private Double nota;

    private LocalDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private STATUS_TAREFA_GRUPO statusTarefaGrupo = STATUS_TAREFA_GRUPO.PENDENTE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_id", referencedColumnName = "id")
    private Upload upload;
    
    private String consideracoes;
    
    @OneToMany(mappedBy = "tarefaGrupo", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Chat> chats;
}
