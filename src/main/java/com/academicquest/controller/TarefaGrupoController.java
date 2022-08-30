package com.academicquest.controller;

import com.academicquest.dto.tarefaGrupo.TarefaGrupoDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoDetalhesDto;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoPutDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoSimplesDTO;
import com.academicquest.service.TarefaGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefa/grupo")
public class TarefaGrupoController {

    @Autowired
    private TarefaGrupoService tarefaGrupoService;

    @GetMapping("/{tarefaId}")
    private ResponseEntity<List<TarefaGrupoSimplesDTO>> buscarPorTarefaId(@PathVariable Long tarefaId) {
        List<TarefaGrupoSimplesDTO> listaTarefaGrupoSimplesDTO = tarefaGrupoService.buscarPorTarefaId(tarefaId);
        return ResponseEntity.ok().body(listaTarefaGrupoSimplesDTO);
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<TarefaGrupoDTO> buscarPorId(@PathVariable Long id) {
        TarefaGrupoDTO tarefaGrupoDTO = tarefaGrupoService.buscarPorId(id);
        return ResponseEntity.ok().body(tarefaGrupoDTO);
    }

    @PutMapping("/{tarefaGrupoId}")
    private ResponseEntity<?> avaliarTarefaGrupo(@PathVariable Long tarefaGrupoId, @RequestBody TarefaGrupoPutDTO tarefaGrupoPutDTO) {
        tarefaGrupoService.avaliarTarefaGrupo(tarefaGrupoId,tarefaGrupoPutDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/entregues")
    private ResponseEntity<List<TarefaGrupoDetalhesDto>> buscarTarefasEntregues() {
        List<TarefaGrupoDetalhesDto> listaTarefaGrupoDetalhesDto = tarefaGrupoService.buscarTarefasEntregues();
        return ResponseEntity.ok().body(listaTarefaGrupoDetalhesDto);
    }

    @GetMapping("/job")
    private ResponseEntity jobTarefasNaoEntregues() {
        tarefaGrupoService.jobModificarStatusTarefasNaoEntregues();
        return ResponseEntity.ok().build();
    }

}
