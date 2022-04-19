package com.academicquest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@PostMapping
	private ResponseEntity<?> save(@RequestBody GrupoPostDTO dto) {
		Boolean flag = grupoService.save(dto);
		if (flag) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/materia/{id}")
	private ResponseEntity<List<GrupoMateriaDTO>> getByMateriaId(@PathVariable Long id) {
		List<GrupoMateriaDTO> grupoMateriaDtoList = grupoService.getByMateriaId(id);
		return ResponseEntity.ok(grupoMateriaDtoList);
		
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<GrupoDTO> getById(@PathVariable Long id) {
		GrupoDTO grupoDTO = grupoService.getById(id);
		return ResponseEntity.ok(grupoDTO);
	}
	

	
	

}
