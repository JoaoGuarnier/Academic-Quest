package com.academicquest.service;

import com.academicquest.dto.TarefaDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.dto.TarefaProjetoDTO;
import com.academicquest.model.*;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import com.academicquest.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;


    public TarefaDTO save(TarefaPostDTO tarefaPostDto) throws IOException {

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
        
        gerarRegistrosTarefaGrupo(tarefaSalva);
        return tarefaDTO;

    }

    private void gerarRegistrosTarefaGrupo(Tarefa tarefaSalva) {

        List<Long> idsGrupos = grupoRepository.buscaGruposPorMateria(tarefaSalva.getProjeto().getMateria().getId());

        idsGrupos.stream().forEach(idGrupo -> {
            Grupo grupo = grupoRepository.findById(idGrupo).get();
            TarefaGrupo tarefaGrupo = new TarefaGrupo();
            tarefaGrupo.setGrupo(grupo);
            tarefaGrupo.setTarefa(tarefaSalva);
            tarefaGrupoRepository.save(tarefaGrupo);
        });



    }

    public TarefaDTO getById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return new TarefaDTO(tarefa);
    }

    public List<TarefaProjetoDTO> getByProjetoId(Long projetoId) {
        List<Long> tarefaIds = tarefaRepository.findIdByProjetoId(projetoId);
        List<TarefaProjetoDTO> tarefaDTOList = new ArrayList<>();
        tarefaIds.stream().forEach(tarefaId -> {
            Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new EntityNotFoundException());
            tarefaDTOList.add(new TarefaProjetoDTO(tarefa));
        });

        return tarefaDTOList;

    }


    private Upload getUpload(TarefaPostDTO tarefaPostDto) throws IOException {
        Upload upload = new Upload();
        upload.setTitulo(tarefaPostDto.getArquivoUpload().getOriginalFilename());
        upload.setFormato(tarefaPostDto.getArquivoUpload().getContentType());
        upload.setArquivoUpload(tarefaPostDto.getArquivoUpload().getBytes());
        return upload;
    }

}
