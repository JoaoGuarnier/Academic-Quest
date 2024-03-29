package com.academicquest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.materia.MateriaDTO;
import com.academicquest.model.Materia;
import com.academicquest.repository.MateriaRepository;
import com.academicquest.service.exception.MateriaNaoEncontradaException;

@Service
public class MateriaService {

	@Autowired
	private MateriaRepository materiaRepository;

	@Transactional(readOnly = true)
	public List<MateriaDTO> buscarTodos() {
		return materiaRepository.findAll().stream().map(MateriaDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<MateriaDTO> buscarPorTurmaId(Long idTurma, Long idUser) throws Exception {
		List<MateriaDTO> materiaDTOs = materiaRepository.findByTurmaId(idTurma, idUser).stream().map(MateriaDTO::new).collect(Collectors.toList());
		return materiaDTOs;
	}

	public MateriaDTO buscarPorId(Long id) {
		Materia materia = materiaRepository.findById(id)
				.orElseThrow(() -> new MateriaNaoEncontradaException("Matéria não encontrada"));
		return new MateriaDTO(materia);
	}
}
