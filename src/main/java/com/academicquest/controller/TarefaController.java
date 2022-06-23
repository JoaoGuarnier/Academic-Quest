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
    private ResponseEntity criarTarefa(MultipartFile arquivoUpload,String nome, String descricao, String dataEntrega, Long idProjeto) {

        try{
            TarefaPostDTO tarefaPostDto = TarefaPostDTO.builder().nome(nome).descricao(descricao).arquivoUpload(arquivoUpload).dataEntrega(dataEntrega).idProjeto(idProjeto).build();
            TarefaDTO tarefaSalva = tarefaService.criarTarefa(tarefaPostDto);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(tarefaSalva.getId()).toUri();

            return ResponseEntity.created(uri).body(tarefaSalva);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    private ResponseEntity<TarefaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tarefaService.getById(id));
    }

    @GetMapping("/projeto/{id}")
    private ResponseEntity<List<TarefaProjetoDTO>> getByProjetoId(@PathVariable Long id) {
        List<TarefaProjetoDTO> tarefas = tarefaService.getByProjetoId(id);
        return ResponseEntity.ok().body(tarefas);
    }
    


}
