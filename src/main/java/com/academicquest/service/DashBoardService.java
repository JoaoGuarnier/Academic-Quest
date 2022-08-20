package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.dashboard.DashBoardDTO;
import com.academicquest.dto.dashboard.DashBoardTarefaDTO;
import com.academicquest.dto.dashboard.DashBoardTarefaGrupoDTO;
import com.academicquest.model.Projeto;
import com.academicquest.model.Tarefa;
import com.academicquest.repository.ProjetoRepository;
import com.academicquest.repository.TarefaRepository;

@Service
public class DashBoardService {
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	public DashBoardDTO buscarDashBoard(Long projetoId) {
		DashBoardDTO dashBoardDTO = new DashBoardDTO();
		
		Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(() -> new EntityNotFoundException("Projeto nao encontrado"));;
				
		dashBoardDTO.getResult().setNomeProjeto(projeto.getNome());
		dashBoardDTO.getResult().setProjetoId(projeto.getId());
		dashBoardDTO.getResult().setStatusProjeto(projeto.getStatus());
		
		List<Tarefa> listaTarefas = tarefaRepository.findByProjetoId(projetoId);
		
		List<DashBoardTarefaDTO> listaDashBoardTarefaDTO = new ArrayList<>();
		
		listaTarefas.stream().forEach(tarefa -> {
			
			DashBoardTarefaDTO dashBoardTarefaDTO = new DashBoardTarefaDTO();
			
			dashBoardTarefaDTO.setNomeTarefa(tarefa.getNome());
			dashBoardTarefaDTO.setDataEntrega(tarefa.getDataEntrega().toString());
			dashBoardTarefaDTO.setStatus(tarefa.getStatus());
			
			List<DashBoardTarefaGrupoDTO> listaDashBoardTarefaGrupoDTO = new ArrayList<>();
			
			List<Tuple> listaTuple = tarefaRepository.buscarTarefaGrupoPorTarefaId(tarefa.getId());
			
			listaTuple.stream().forEach(tuple -> {
				
				DashBoardTarefaGrupoDTO dashBoardTarefaGrupoDTO = new DashBoardTarefaGrupoDTO();
			
				dashBoardTarefaGrupoDTO.setTarefaGrupoId(Long.valueOf(tuple.get(0).toString()));
				dashBoardTarefaGrupoDTO.setStatus(tuple.get(1).toString());
				dashBoardTarefaGrupoDTO.setNomeGrupo(tuple.get(2).toString());
				
				listaDashBoardTarefaGrupoDTO.add(dashBoardTarefaGrupoDTO);
			});
			dashBoardTarefaDTO.setTarefasGrupos(listaDashBoardTarefaGrupoDTO);
			listaDashBoardTarefaDTO.add(dashBoardTarefaDTO);
		});
		
		dashBoardDTO.getResult().setTarefas(listaDashBoardTarefaDTO);
		return dashBoardDTO;
	}
}
