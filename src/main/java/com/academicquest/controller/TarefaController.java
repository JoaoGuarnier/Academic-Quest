package com.academicquest.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.academicquest.dto.TarefaDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.dto.TarefaProjetoDTO;
import com.academicquest.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    private ResponseEntity<TarefaDTO> salvar(MultipartFile arquivoUpload,String nome, String descricao, String dataEntrega, Long projetoId) throws IOException {
        TarefaPostDTO tarefaPostDto = TarefaPostDTO.builder().nome(nome)
                .descricao(descricao).arquivoUpload(arquivoUpload)
                .dataEntrega(dataEntrega).projetoId(projetoId).build();
        TarefaDTO tarefaSalva = tarefaService.salvar(tarefaPostDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tarefaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(tarefaSalva);
    }

    @GetMapping("/{id}")
    private ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        TarefaDTO tarefaDTO = tarefaService.buscarPorId(id);
        return ResponseEntity.ok().body(tarefaDTO);
    }

    @GetMapping("/projeto/{id}")
    private ResponseEntity<List<TarefaProjetoDTO>> buscarPorProjetoId(@PathVariable Long id) {
        List<TarefaProjetoDTO> listaTarefas = tarefaService.buscarPorProjetoId(id);
        return ResponseEntity.ok().body(listaTarefas);
    }

}
