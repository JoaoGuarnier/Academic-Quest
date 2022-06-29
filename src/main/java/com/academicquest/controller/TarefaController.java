package com.academicquest.controller;

import com.academicquest.dto.TarefaDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.dto.TarefaProjetoDTO;
import com.academicquest.model.Tarefa;
import com.academicquest.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @PostMapping
    private ResponseEntity salvar(MultipartFile arquivoUpload,String nome, String descricao, String dataEntrega, Long projetoId) {

        try{
            TarefaPostDTO tarefaPostDto = TarefaPostDTO.builder().nome(nome).descricao(descricao).arquivoUpload(arquivoUpload).dataEntrega(dataEntrega).projetoId(projetoId).build();
            TarefaDTO tarefaSalva = tarefaService.salvar(tarefaPostDto);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(tarefaSalva.getId()).toUri();

            return ResponseEntity.created(uri).body(tarefaSalva);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    private ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(tarefaService.buscarPorId(id));
    }

    @GetMapping("/projeto/{id}")
    private ResponseEntity<List<TarefaProjetoDTO>> buscarPorProjetoId(@PathVariable Long id) {
        List<TarefaProjetoDTO> listaTarefas = tarefaService.buscarPorProjetoId(id);
        return ResponseEntity.ok().body(listaTarefas);
    }
    


}
