package com.academicquest.service;

import com.academicquest.dto.TarefaDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.model.Upload;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaRepository;
import com.academicquest.model.Projeto;
import com.academicquest.model.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;



    public TarefaDTO criarTarefa(TarefaPostDTO tarefaPostDto) throws IOException {

        Tarefa tarefa = new Tarefa();
        tarefa.setNome(tarefaPostDto.getNome());
        tarefa.setDescricao(tarefaPostDto.getDescricao());
        tarefa.setDataEntrega(LocalDate.parse(tarefaPostDto.getDataEntrega()));

        Upload upload = getUpload(tarefaPostDto);

        tarefa.setUpload(upload);

        Projeto projeto = projetoRepository.findById(tarefaPostDto.getIdProjeto()).orElseThrow(() -> new EntityNotFoundException());

        tarefa.setProjeto(projeto);

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        TarefaDTO tarefaDTO = new TarefaDTO(tarefaSalva, tarefaPostDto.getArquivoUpload().getOriginalFilename());

        return tarefaDTO;

    }

    private Upload getUpload(TarefaPostDTO tarefaPostDto) throws IOException {
        Upload upload = new Upload();
        upload.setTitulo(tarefaPostDto.getArquivoUpload().getOriginalFilename());
        upload.setFormato(tarefaPostDto.getArquivoUpload().getContentType());
        upload.setArquivoUpload(tarefaPostDto.getArquivoUpload().getBytes());
        return upload;
    }

    public TarefaDTO getById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return new TarefaDTO(tarefa);
    }

}
