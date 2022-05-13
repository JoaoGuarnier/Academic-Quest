package com.academicquest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.MateriaDTO;
import com.academicquest.model.Materia;
import com.academicquest.repository.MateriaRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MateriaService {
	
	@Autowired
	private MateriaRepository materiaRepository;

	@Transactional(readOnly = true)
	public List<MateriaDTO> getAll() {
		return materiaRepository.findAll().stream().map(MateriaDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<MateriaDTO> getByTurmaId(Long id) {
		 return materiaRepository.findByTurmaId(id).stream().map(MateriaDTO::new).collect(Collectors.toList());
	}

}
