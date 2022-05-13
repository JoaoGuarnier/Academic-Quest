package com.academicquest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.TurmaDTO;
import com.academicquest.model.Turma;
import com.academicquest.repository.TurmaRepository;

@Service
public class TurmaService {
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	public List<TurmaDTO> getAll() {
		return turmaRepository.findAll().stream().map(TurmaDTO::new).collect(Collectors.toList());
	}

}
