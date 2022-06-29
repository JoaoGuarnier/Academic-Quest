package com.academicquest.dto;

import com.academicquest.model.Upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDTO {

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
