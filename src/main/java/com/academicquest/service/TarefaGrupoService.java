package com.academicquest.service;

import com.academicquest.dto.*;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TarefaGrupoService {

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public List<TarefaGrupoSimplesDTO> buscarPorTarefaId(Long tarefaId) {
        List<TarefaGrupo> tarefaGrupos = tarefaGrupoRepository.findByTarefaId(tarefaId);
        List<TarefaGrupoSimplesDTO> tarefaGrupoSimplesDTOS = tarefaGrupos.stream().map(TarefaGrupoSimplesDTO::new).collect(Collectors.toList());
        List<TarefaGrupoSimplesDTO> entregues = new ArrayList<>();
        List<TarefaGrupoSimplesDTO> pendentes = new ArrayList<>();
        List<TarefaGrupoSimplesDTO> corrigidas = new ArrayList<>();
        
        tarefaGrupoSimplesDTOS.stream().forEach(tarefaGrupoSimplesDTO -> {
            if (tarefaGrupoSimplesDTO.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.ENTREGUE) {
                entregues.add(tarefaGrupoSimplesDTO);
            }
            if (tarefaGrupoSimplesDTO.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.PENDENTE) {
                pendentes.add(tarefaGrupoSimplesDTO);
            }
            if (tarefaGrupoSimplesDTO.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.CORRIGIDA) {
                corrigidas.add(tarefaGrupoSimplesDTO);
            }
        });

        List<TarefaGrupoSimplesDTO> listaFinal = new ArrayList<>();
        listaFinal.addAll(entregues);
        listaFinal.addAll(pendentes);
        listaFinal.addAll(corrigidas);

        return listaFinal;
    }

    @Transactional(readOnly = true)
    public TarefaGrupoDTO buscarPorId(Long id) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        TarefaGrupoDTO tarefaGrupoDTO = converterParaDto(tarefaGrupo);
        return tarefaGrupoDTO;
    }

    private TarefaGrupoDTO converterParaDto(TarefaGrupo tarefaGrupo) {
        try {
            TarefaGrupoDTO tarefaGrupoDTO = new TarefaGrupoDTO();
            tarefaGrupoDTO.setId(tarefaGrupo.getId());
            tarefaGrupoDTO.setNomeGrupo(tarefaGrupo.getGrupo().getNome());
            tarefaGrupoDTO.setNomeTarefa(tarefaGrupo.getTarefa().getNome());
            tarefaGrupoDTO.setStatusTarefaGrupo(tarefaGrupo.getStatusTarefaGrupo());
            tarefaGrupoDTO.setConsideracoes(tarefaGrupo.getConsideracoes());
            tarefaGrupoDTO.setNota(tarefaGrupo.getNota());
            tarefaGrupoDTO.setDataEntrega(tarefaGrupo.getDataEntrega());
            tarefaGrupoDTO.setUpload(tarefaGrupo.getUpload() != null ? new UploadDTO(tarefaGrupo.getUpload()) : null);
            return tarefaGrupoDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para tarefa grupo dto");
        }
    }


    @Transactional()
    public void avaliarTarefaGrupo(Long idTarefaGrupo, TarefaGrupoPutDTO tarefaGrupoPutDTO) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(idTarefaGrupo).orElseThrow(() -> new EntityNotFoundException());
        tarefaGrupo.setNota(tarefaGrupoPutDTO.getNota());
        tarefaGrupo.setConsideracoes(tarefaGrupoPutDTO.getConsideracoes());
        tarefaGrupo.setStatusTarefaGrupo(STATUS_TAREFA_GRUPO.CORRIGIDA);
        tarefaGrupoRepository.save(tarefaGrupo);
    }
}
