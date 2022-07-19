package com.academicquest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.academicquest.dto.ProjetoDTO;
import com.academicquest.dto.ProjetoPostDTO;
import com.academicquest.dto.ProjetoPutDTO;
import com.academicquest.service.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    private ResponseEntity<List<ProjetoDTO>> buscarTodos() {
        List<ProjetoDTO> listaProjetos = projetoService.buscarTodos();
        return ResponseEntity.ok().body(listaProjetos);

    }

    @GetMapping("/{id}")
    private ResponseEntity<ProjetoDTO> buscarPorId(@PathVariable Long id) {
        ProjetoDTO projetoDTO = projetoService.buscarPorId(id);
        return ResponseEntity.ok().body(projetoDTO);
    }

    @GetMapping("/materia/{id}")
    private ResponseEntity<List<ProjetoDTO>> buscarPorMateriaId(@PathVariable Long id) {
        List<ProjetoDTO> listaProjetos = projetoService.buscarPorMateriaId(id);
        return ResponseEntity.ok().body(listaProjetos);
    }

    @PostMapping
    private ResponseEntity<ProjetoDTO> salvar(@RequestBody ProjetoPostDTO projetoPostDTO) {
        ProjetoDTO projetoDTO = projetoService.salvar(projetoPostDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(projetoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(projetoDTO);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProjetoDTO> atualizar(@RequestBody ProjetoPutDTO projetoPutDTO, @PathVariable Long id) {
        ProjetoDTO projetoDTO = projetoService.atualizarProjeto(projetoPutDTO, id);
        return ResponseEntity.ok().body(projetoDTO);
    }

    @PostMapping("/avaliar/{projetoId}")
    private ResponseEntity<?> avaliarProjeto(@PathVariable Long projetoId) {
        projetoService.avaliarProjeto(projetoId);
        return ResponseEntity.ok().build();
    }

}
