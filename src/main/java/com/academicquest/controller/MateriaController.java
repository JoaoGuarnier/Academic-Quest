package com.academicquest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.MateriaDTO;
import com.academicquest.service.MateriaService;

@RestController
@RequestMapping("/materias")
public class MateriaController {
	
	@Autowired
	private MateriaService materiaService;
	
	@GetMapping
	private ResponseEntity<List<MateriaDTO>> getAll() {
		List<MateriaDTO> listMateriaDTO = materiaService.getAll();
		return ResponseEntity.ok(listMateriaDTO);
	}
	
	@GetMapping("/turma/{id}")
	private ResponseEntity<List<MateriaDTO>> getByTurmaId(@PathVariable Long id) {
		List<MateriaDTO> listMateriaDTO = materiaService.getByTurmaId(id);
		return ResponseEntity.ok(listMateriaDTO);
	}

}
