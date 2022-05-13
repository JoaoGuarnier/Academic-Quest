package com.academicquest.dto;

import com.academicquest.model.Materia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoPostDTO implements Serializable {

    private String nome;
    private String descricao;
    private Long idMateria;

}
