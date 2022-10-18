package com.academicquest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.tarefaGrupo.TarefaGrupoDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoDetalhesDto;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoPutDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoSimplesDTO;
import com.academicquest.service.TarefaGrupoService;

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

    @GetMapping("/entregues/professorId/{professorId}")
    private ResponseEntity<List<TarefaGrupoDetalhesDto>> buscarTarefasEntregues(@PathVariable Long professorId) {
        List<TarefaGrupoDetalhesDto> listaTarefaGrupoDetalhesDto = tarefaGrupoService.buscarTarefasEntregues(professorId);
        return ResponseEntity.ok().body(listaTarefaGrupoDetalhesDto);
    }

    @GetMapping("/job")
    private ResponseEntity<?> jobTarefasNaoEntregues() {
        tarefaGrupoService.jobModificarStatusTarefasNaoEntregues();
        return ResponseEntity.ok().build();
    }

}
