package com.academicquest.dto;

import com.academicquest.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaPostDTO implements Serializable{

    private Long id;

    private String nome;

    private String descricao;

    private String dataEntrega;

    private MultipartFile arquivoUpload;

    private Long projetoId;

}
