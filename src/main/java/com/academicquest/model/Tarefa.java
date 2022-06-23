package com.academicquest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Blob;
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

    @Lob
    private Blob arquivoUpload;

    @ManyToOne(fetch = FetchType.LAZY)
    private Projeto projeto;
}
