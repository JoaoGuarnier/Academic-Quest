package com.academicquest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;

	@PostMapping
	private ResponseEntity<GrupoDTO> salvar(@RequestBody GrupoPostDTO grupoPostDTO) {
		GrupoDTO grupoDTO = grupoService.salvar(grupoPostDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(grupoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(grupoDTO);
	}
	
	@GetMapping("/materia/{id}")
	private ResponseEntity<List<GrupoMateriaDTO>> buscarPorMateriaId(@PathVariable Long id) {
		List<GrupoMateriaDTO> grupoMateriaDtoList = grupoService.buscarPorMateriaId(id);
		return ResponseEntity.ok().body(grupoMateriaDtoList);
		
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<GrupoDTO> buscarPorId(@PathVariable Long id) {
		GrupoDTO grupoDTO = grupoService.buscarPorId(id);
		return ResponseEntity.ok().body(grupoDTO);
	}

	@GetMapping("/alunos/materia/{id}")
	private ResponseEntity<List<UserDTO>> buscarAlunosSemGrupoPorIdMateria(@PathVariable Long id) {
		List<UserDTO> listaUserDTO = grupoService.buscarAlunosSemGrupo(id);
		return ResponseEntity.ok().body(listaUserDTO);
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<GrupoUpdateDTO> atualizar(@RequestBody GrupoUpdateDTO grupoUpdateDTO, @PathVariable Long id) {
		grupoUpdateDTO = grupoService.atualizarGrupo(grupoUpdateDTO, id);
		return ResponseEntity.ok().body(grupoUpdateDTO);
	}
}
