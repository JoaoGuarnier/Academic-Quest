package com.academicquest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliarProjetoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projetoId;

    private Long grupoId;
}
