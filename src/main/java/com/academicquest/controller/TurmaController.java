package com.academicquest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.TurmaDTO;
import com.academicquest.service.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
	
	@Autowired
	private TurmaService turmaService;
	
	@GetMapping
	private ResponseEntity<List<TurmaDTO>> getAll() {
		List<TurmaDTO> listTurmaDto = turmaService.getAll();
		return ResponseEntity.ok(listTurmaDto);
	}
}
