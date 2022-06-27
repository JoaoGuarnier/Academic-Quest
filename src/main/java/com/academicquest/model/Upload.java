package com.academicquest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_upload")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String formato;

//    @OneToOne(mappedBy = "upload")
//    @JsonIgnore
//    private Tarefa tarefa;

    @Lob
    private byte[] arquivoUpload;

}
