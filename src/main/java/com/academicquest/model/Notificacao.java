package com.academicquest.model;

import com.academicquest.enums.TIPO_NOTIFICACAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_notificacao")
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUserCriadorNotificacao;
    private Long userNotificado;
    @Enumerated(EnumType.STRING)
    private TIPO_NOTIFICACAO tipoNotificacao;
    private Boolean flagVisto;
    private String dataNotificacao;
    private String mensagem;

}
