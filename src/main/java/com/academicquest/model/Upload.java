package com.academicquest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_upload")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String formato;

    @OneToOne(mappedBy = "upload")
    private Tarefa tarefa;

    @Lob
    private byte[] arquivoUpload;

}
