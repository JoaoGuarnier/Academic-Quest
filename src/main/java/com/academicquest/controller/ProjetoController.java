package com.academicquest.controller;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    private ResponseEntity<List<ProjetoDTO>> getAll() {
        List<ProjetoDTO> projetos = projetoService.getAll();
        return ResponseEntity.ok(projetos);

    }

    @GetMapping("/materia/{id}")
    private ResponseEntity<List<ProjetoDTO>> getByMateriaId(@PathVariable Long id) {
        List<ProjetoDTO> projetos = projetoService.getByMateriaId(id);
        return ResponseEntity.ok(projetos);
    }

    @PostMapping
    private ResponseEntity save(@RequestBody ProjetoPostDTO projetoPostDTO) {
        projetoService.save(projetoPostDTO);
        return ResponseEntity.ok().build();
    }

}
