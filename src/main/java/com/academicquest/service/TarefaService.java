package com.academicquest.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.TarefaDTO;
import com.academicquest.dto.TarefaPostDTO;
import com.academicquest.dto.TarefaProjetoDTO;
import com.academicquest.model.Grupo;
import com.academicquest.model.Projeto;
import com.academicquest.model.Tarefa;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.Upload;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import com.academicquest.repository.TarefaRepository;
import com.academicquest.service.exception.ProjetoNaoEncontradoException;
import com.academicquest.service.exception.TarefaNaoEncontradaException;

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

    @Transactional
    public TarefaDTO salvar(TarefaPostDTO tarefaPostDto) throws IOException {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(tarefaPostDto.getNome());
        tarefa.setDescricao(tarefaPostDto.getDescricao());
        tarefa.setDataEntrega(LocalDate.parse(tarefaPostDto.getDataEntrega()));
        Upload upload = buscarUpload(tarefaPostDto);
        tarefa.setUpload(upload);
        Projeto projeto = projetoRepository.findById(tarefaPostDto.getProjetoId()).orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado"));
        tarefa.setProjeto(projeto);
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        TarefaDTO tarefaDTO = new TarefaDTO(tarefaSalva, tarefaPostDto.getArquivoUpload().getOriginalFilename());
        gerarRegistrosTarefaGrupo(tarefaSalva);
        return tarefaDTO;
    }

    @Transactional
    private void gerarRegistrosTarefaGrupo(Tarefa tarefaSalva) {
        List<Long> idsGrupos = grupoRepository.buscaGruposPorMateriaId(tarefaSalva.getProjeto().getMateria().getId());
        idsGrupos.stream().forEach(idGrupo -> {
            Grupo grupo = grupoRepository.findById(idGrupo).get();
            TarefaGrupo tarefaGrupo = new TarefaGrupo();
            tarefaGrupo.setGrupo(grupo);
            tarefaGrupo.setTarefa(tarefaSalva);
            tarefaGrupoRepository.save(tarefaGrupo);
        });
    }

    @Transactional(readOnly = true)
    public TarefaDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada"));
        return new TarefaDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public List<TarefaProjetoDTO> buscarPorProjetoId(Long projetoId) {
        List<Long> tarefaIds = tarefaRepository.buscarIdsTarefasPorProjetoId(projetoId);
        List<TarefaProjetoDTO> tarefaDTOList = new ArrayList<>();
        tarefaIds.stream().forEach(tarefaId -> {
            Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada"));
            tarefaDTOList.add(new TarefaProjetoDTO(tarefa));
        });
        return tarefaDTOList;
    }

    private Upload buscarUpload(TarefaPostDTO tarefaPostDto) throws IOException {
        Upload upload = new Upload();
        upload.setTitulo(tarefaPostDto.getArquivoUpload().getOriginalFilename());
        upload.setFormato(tarefaPostDto.getArquivoUpload().getContentType());
        upload.setArquivoUpload(tarefaPostDto.getArquivoUpload().getBytes());
        return upload;
    }

}
