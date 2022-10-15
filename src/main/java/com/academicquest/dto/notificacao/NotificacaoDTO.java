package com.academicquest.dto.notificacao;

import com.academicquest.model.Notificacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacaoDTO {

    private Long id;
    private String data;
    private String mensagem;

    public NotificacaoDTO(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.data = notificacao.getDataNotificacao();
        this.mensagem = notificacao.getMensagem();
    }

}
