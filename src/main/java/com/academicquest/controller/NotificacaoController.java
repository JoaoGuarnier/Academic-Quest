package com.academicquest.controller;

import com.academicquest.dto.notificacao.NotificacaoDTO;
import com.academicquest.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PutMapping("/lida/{notificacaoId}")
    private ResponseEntity marcarComoLido(@PathVariable Long notificacaoId) {
        notificacaoService.marcarComoVisto(notificacaoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    private ResponseEntity<List<NotificacaoDTO>> buscarNotificacoesPorUserId(@PathVariable Long userId) {
        List<NotificacaoDTO> notificacaoDTOS = notificacaoService.buscarNotificacoesUser(userId);
        return ResponseEntity.ok().body(notificacaoDTOS);
    }

}
