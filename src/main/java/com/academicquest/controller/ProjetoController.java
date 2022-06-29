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
    private ResponseEntity<List<ProjetoDTO>> buscarTodos() {
        List<ProjetoDTO> listaProjetos = projetoService.buscarTodos();
        return ResponseEntity.ok(listaProjetos);

    }

    @GetMapping("/materia/{id}")
    private ResponseEntity<List<ProjetoDTO>> buscarPorMateriaId(@PathVariable Long id) {
        List<ProjetoDTO> listaProjetos = projetoService.buscarPorMateriaId(id);
        return ResponseEntity.ok(listaProjetos);
    }

    @PostMapping
    private ResponseEntity salvar(@RequestBody ProjetoPostDTO projetoPostDTO) {
        projetoService.salvar(projetoPostDTO);
        return ResponseEntity.ok().build();
    }

}
