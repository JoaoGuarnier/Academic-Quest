package com.academicquest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.DashDTO;
import com.academicquest.enums.STATUS_TAREFA_GRUPO;
import com.academicquest.model.Dash;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.User;
import com.academicquest.repository.DashRepository;
import com.academicquest.repository.TarefaGrupoRepository;

@Service
public class DashService {
	
	@Autowired
	private DashRepository dashRepository;
	
	
	@Autowired
	private TarefaGrupoRepository tarefaGrupoRepository;
	
	
	public DashDTO buscaStatus() {
		DashDTO b = new DashDTO();
		List<TarefaGrupo> a = tarefaGrupoRepository.findAll();
		
		a.stream().forEach(m -> {
			if(m.getStatusTarefaGrupo().equals(STATUS_TAREFA_GRUPO.CORRIGIDA)) {
				b.setTotalCorrigida(b.getTotalCorrigida() + 1);
			} else if (m.getStatusTarefaGrupo().equals(STATUS_TAREFA_GRUPO.ENTREGUE)) {
				b.setTotalEntregue(b.getTotalEntregue() + 1);
			} else if (m.getStatusTarefaGrupo().equals(STATUS_TAREFA_GRUPO.NAO_ENTREGUE)) {
				b.setTotalNaoEntregue(b.getTotalNaoEntregue() + 1);
			}else if (m.getStatusTarefaGrupo().equals(STATUS_TAREFA_GRUPO.PENDENTE)) {
				b.setTotalPendente(b.getTotalPendente() + 1);
			}
		});
		
		
//		for(TarefaGrupo dash : a) {
//			if(dash.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.CORRIGIDA) {
//				b.setTotalCorrigida(b.getTotalCorrigida() + 1);
//			} else if (dash.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.ENTREGUE) {
//				b.setTotalEntregue(b.getTotalEntregue() + 1);
//			} else if (dash.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.NAO_ENTREGUE) {
//				b.setTotalNaoEntregue(b.getTotalNaoEntregue() + 1);
//			}else if (dash.getStatusTarefaGrupo() == STATUS_TAREFA_GRUPO.PENDENTE) {
//				b.setTotalPendente(b.getTotalPendente() + 1);
//			}
//			
//		}
		
		return b;
	}

}
