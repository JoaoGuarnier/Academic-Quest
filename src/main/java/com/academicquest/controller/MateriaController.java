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
	private ResponseEntity<List<MateriaDTO>> buscarTodos() {
		List<MateriaDTO> listaMateriaDTO = materiaService.buscarTodos();
		return ResponseEntity.ok().body(listaMateriaDTO);
	}

	@GetMapping("/{id}")
	private ResponseEntity<MateriaDTO> buscarPorId(@PathVariable Long id) {
		MateriaDTO materiaDTO = materiaService.buscarPorId(id);
		return ResponseEntity.ok().body(materiaDTO);
	}
	
	@GetMapping("/turma/{id}")
	private ResponseEntity<List<MateriaDTO>> buscarPorTurmaId(@PathVariable Long id) {
		List<MateriaDTO> listaMateriaDTO = materiaService.buscarPorTurmaId(id);
		return ResponseEntity.ok().body(listaMateriaDTO);
	}

}
