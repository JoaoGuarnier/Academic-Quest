package com.academicquest.dto;

import com.academicquest.model.Upload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDTO implements Serializable{

    private Long id;

    private String titulo;

    private String formato;

    private byte[] arquivoUpload;

    public UploadDTO(Upload upload) {
        this.id = upload.getId();
        this.titulo = upload.getTitulo();
        this.formato = upload.getFormato();
        this.arquivoUpload = upload.getArquivoUpload();
    }

}
