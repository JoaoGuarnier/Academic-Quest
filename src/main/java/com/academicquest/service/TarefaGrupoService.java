package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.academicquest.dto.tarefaGrupo.TarefaGrupoDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.chat.ChatDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoPutDTO;
import com.academicquest.dto.tarefaGrupo.TarefaGrupoSimplesDTO;
import com.academicquest.dto.upload.UploadDTO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.repository.ChatRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import com.academicquest.service.exception.TarefaGrupoNaoEncontradoException;

import java.time.LocalDate;

@Service
public class TarefaGrupoService {

    @Autowired
    private TarefaGrupoRepository tarefaGrupoRepository;
	
	@Autowired
	private ChatRepository chatRepository;

    @Autowired
    private NotificacaoService notificacaoService;
    
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

    @Transactional
    public List<TarefaGrupoDetalhesDto> buscarTarefasEntregues(Long id) {
        return tarefaGrupoRepository.buscarTarefasPorStatus(id).stream().map(TarefaGrupoDetalhesDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TarefaGrupoDTO buscarPorId(Long id) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(id).orElseThrow(() -> new TarefaGrupoNaoEncontradoException("Tarefa grupo não encontrado"));
        TarefaGrupoDTO tarefaGrupoDTO = converterParaDto(tarefaGrupo);
        return tarefaGrupoDTO;
    }

    private TarefaGrupoDTO converterParaDto(TarefaGrupo tarefaGrupo) {
        try {
            TarefaGrupoDTO tarefaGrupoDTO = new TarefaGrupoDTO();
    		List<ChatDTO> chatDto = chatRepository.findByTarefaGrupoId(tarefaGrupo.getId()).stream().map(ChatDTO::new).collect(Collectors.toList());            tarefaGrupoDTO.setId(tarefaGrupo.getId());
            tarefaGrupoDTO.setNomeGrupo(tarefaGrupo.getGrupo().getNome());
            tarefaGrupoDTO.setNomeTarefa(tarefaGrupo.getTarefa().getNome());
            tarefaGrupoDTO.setStatusTarefaGrupo(tarefaGrupo.getStatusTarefaGrupo());
            tarefaGrupoDTO.setConsideracoes(tarefaGrupo.getConsideracoes());
            tarefaGrupoDTO.setNota(tarefaGrupo.getNota());
            tarefaGrupoDTO.setDataEntrega(tarefaGrupo.getDataEntrega());
            tarefaGrupoDTO.setUpload(tarefaGrupo.getUpload() != null ? new UploadDTO(tarefaGrupo.getUpload()) : null);
            tarefaGrupoDTO.setChats(chatDto);
            tarefaGrupoDTO.setIdTarefa(tarefaGrupo.getTarefa().getId());
            return tarefaGrupoDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter para tarefa grupo dto");
        }
    }

    @Transactional
    public void avaliarTarefaGrupo(Long idTarefaGrupo, TarefaGrupoPutDTO tarefaGrupoPutDTO) {
        TarefaGrupo tarefaGrupo = tarefaGrupoRepository.findById(idTarefaGrupo).orElseThrow(() -> new TarefaGrupoNaoEncontradoException("Tarefa grupo não encontrado"));
        tarefaGrupo.setNota(tarefaGrupoPutDTO.getNota());
        tarefaGrupo.setConsideracoes(tarefaGrupoPutDTO.getConsideracoes());
        tarefaGrupo.setStatusTarefaGrupo(STATUS_TAREFA_GRUPO.CORRIGIDA);
        tarefaGrupoRepository.save(tarefaGrupo);
        notificacaoService.notificar(tarefaGrupo);
    }

    @Transactional
    public void jobModificarStatusTarefasNaoEntregues() {
        
        List<TarefaGrupo> lista = tarefaGrupoRepository.buscarTarefasNaoEntregue(LocalDate.now());
        System.out.println("Executando job...");
        lista.stream().forEach(x -> x.setStatusTarefaGrupo(STATUS_TAREFA_GRUPO.NAO_ENTREGUE));
        System.out.println("Executando com sucesso...");
    }

}
