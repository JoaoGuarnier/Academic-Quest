package com.academicquest.service;

import com.academicquest.dto.*;
import com.academicquest.model.Grupo;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.repository.GrupoRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaGrupoService {

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public List<TarefaGrupoSimplesDTO> getByTarefaId(Long tarefaId) {
        List<TarefaGrupo> tarefaGrupos = tarefaGrupoRepository.findByTarefaId(tarefaId);
        List<TarefaGrupoSimplesDTO> tarefaGrupoSimplesDTOS = tarefaGrupos.stream().map(TarefaGrupoSimplesDTO::new).collect(Collectors.toList());
        return tarefaGrupoSimplesDTOS;
    }

    public TarefaGrupoDTO getById(Long id) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        TarefaGrupoDTO tarefaGrupoDTO = convertToDto(tarefaGrupo);
        return tarefaGrupoDTO;
    }

    private TarefaGrupoDTO convertToDto(TarefaGrupo tarefaGrupo) {
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


}
