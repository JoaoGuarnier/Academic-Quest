package com.academicquest.controller;

import com.academicquest.dto.TarefaGrupoDTO;
import com.academicquest.dto.TarefaGrupoPutDTO;
import com.academicquest.dto.TarefaGrupoSimplesDTO;
import com.academicquest.service.TarefaGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa/grupo")
public class TarefaGrupoController {

    @Autowired
    private TarefaGrupoService tarefaGrupoService;

    @GetMapping("/{idTarefa}")
    private ResponseEntity<List<TarefaGrupoSimplesDTO>> getByTarefaId(@PathVariable Long idTarefa) {
        List<TarefaGrupoSimplesDTO> list = tarefaGrupoService.getByTarefaId(idTarefa);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<TarefaGrupoDTO> getById(@PathVariable Long id) {
        TarefaGrupoDTO tarefaGrupoDTO = tarefaGrupoService.getById(id);
        return ResponseEntity.ok().body(tarefaGrupoDTO);
    }

    @PutMapping("/{idTarefaGrupo}")
    private ResponseEntity avaliarTarefaGrupo(@PathVariable Long idTarefaGrupo, @RequestBody TarefaGrupoPutDTO tarefaGrupoPutDTO) {
        tarefaGrupoService.avaliarTarefaGrupo(idTarefaGrupo,tarefaGrupoPutDTO);
        return ResponseEntity.ok().build();
    }



}
