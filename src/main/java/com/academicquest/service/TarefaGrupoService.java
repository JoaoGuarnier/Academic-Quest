package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.TarefaGrupoDTO;
import com.academicquest.dto.TarefaGrupoPutDTO;
import com.academicquest.dto.TarefaGrupoSimplesDTO;
import com.academicquest.dto.UploadDTO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.repository.ChatRepository;
import com.academicquest.repository.TarefaGrupoRepository;

@Service
public class TarefaGrupoService {

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;
	
	@Autowired
	private ChatRepository chatRepository;
    
    @Transactional
    public List<TarefaGrupoSimplesDTO> getByTarefaId(Long tarefaId) {
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

    public TarefaGrupoDTO getById(Long id) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        TarefaGrupoDTO tarefaGrupoDTO = convertToDto(tarefaGrupo);
        
        
        return tarefaGrupoDTO;
    }

    private TarefaGrupoDTO convertToDto(TarefaGrupo tarefaGrupo) {
        try {
            TarefaGrupoDTO tarefaGrupoDTO = new TarefaGrupoDTO();
    		List<ChatDto> chatDto = chatRepository.findAll().stream().map(ChatDto::new).collect(Collectors.toList());
            tarefaGrupoDTO.setId(tarefaGrupo.getId());
            tarefaGrupoDTO.setNomeGrupo(tarefaGrupo.getGrupo().getNome());
            tarefaGrupoDTO.setNomeTarefa(tarefaGrupo.getTarefa().getNome());
            tarefaGrupoDTO.setStatusTarefaGrupo(tarefaGrupo.getStatusTarefaGrupo());
            tarefaGrupoDTO.setConsideracoes(tarefaGrupo.getConsideracoes());
            tarefaGrupoDTO.setNota(tarefaGrupo.getNota());
            tarefaGrupoDTO.setDataEntrega(tarefaGrupo.getDataEntrega());
            tarefaGrupoDTO.setUpload(tarefaGrupo.getUpload() != null ? new UploadDTO(tarefaGrupo.getUpload()) : null);
            tarefaGrupoDTO.setChats(chatDto);
            return tarefaGrupoDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para tarefa grupo dto");
        }
    }


    public void avaliarTarefaGrupo(Long idTarefaGrupo, TarefaGrupoPutDTO tarefaGrupoPutDTO) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(idTarefaGrupo).orElseThrow(() -> new EntityNotFoundException());
        tarefaGrupo.setNota(tarefaGrupoPutDTO.getNota());
        tarefaGrupo.setConsideracoes(tarefaGrupoPutDTO.getConsideracoes());
        tarefaGrupo.setStatusTarefaGrupo(STATUS_TAREFA_GRUPO.CORRIGIDA);
        tarefaGrupoRepository.save(tarefaGrupo);
    }
}
