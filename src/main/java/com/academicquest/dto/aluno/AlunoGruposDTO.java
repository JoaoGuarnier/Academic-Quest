package com.academicquest.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoGruposDTO {

    private Long id;
    private String nomeGrupo;
    private Long usuarioLider;

}
