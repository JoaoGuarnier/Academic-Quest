package com.academicquest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.TurmaDTO;
import com.academicquest.repository.TurmaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaService {
	
	@Autowired
	private TurmaRepository turmaRepository;

	@Transactional(readOnly = true)
	public List<TurmaDTO> buscarTodos() {
		return turmaRepository.findAll().stream().map(TurmaDTO::new).collect(Collectors.toList());
	}
}
