package com.academicquest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academicquest.dto.MateriaDTO;
import com.academicquest.model.Materia;
import com.academicquest.repository.MateriaRepository;


@Service
public class MateriaService {
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	public List<MateriaDTO> getAll() {
		List<Materia> listMateria = materiaRepository.findAll();
		List<MateriaDTO> listMateriaDto = listMateria.stream().map(MateriaDTO::new).collect(Collectors.toList());
		return listMateriaDto;
	}
	
	public List<MateriaDTO> getByTurmaId(Long id) {
		List<Materia> listMateria = materiaRepository.findByTurmaId(id);
		List<MateriaDTO> listMateriaDto = listMateria.stream().map(MateriaDTO::new).collect(Collectors.toList());
		return listMateriaDto;
	}

}
