package com.academicquest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.model.Grupo;
import com.academicquest.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	
	@PostMapping
	private ResponseEntity salvar(@RequestBody GrupoPostDTO grupoPostDTO) {
		grupoService.salvar(grupoPostDTO);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/materia/{id}")
	private ResponseEntity<List<GrupoMateriaDTO>> buscarPorMateriaId(@PathVariable Long id) {
		List<GrupoMateriaDTO> grupoMateriaDtoList = grupoService.buscarPorMateriaId(id);
		return ResponseEntity.ok(grupoMateriaDtoList);
		
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<GrupoDTO> buscarPorId(@PathVariable Long id) {
		GrupoDTO grupoDTO = grupoService.buscarPorId(id);
		return ResponseEntity.ok(grupoDTO);
	}
	
	
	@GetMapping("/alunos/materia/{id}")
	private ResponseEntity<List<UserDTO>> buscarAlunosSemGrupoPorIdMateria(@PathVariable Long id) {
		List<UserDTO> listaUserDTO = grupoService.buscarAlunosSemGrupo(id);
		return ResponseEntity.ok(listaUserDTO);
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<GrupoUpdateDTO> atualizar(@RequestBody GrupoUpdateDTO grupoUpdateDTO, @PathVariable Long id) {
		grupoUpdateDTO = grupoService.atualizarGrupo(grupoUpdateDTO, id);
		return ResponseEntity.ok(grupoUpdateDTO);
	}

	
	

}
