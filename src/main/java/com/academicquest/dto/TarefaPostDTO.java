package com.academicquest.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaPostDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private String nome;

    private String descricao;

    private String dataEntrega;

    private MultipartFile arquivoUpload;

    private Long projetoId;

}
