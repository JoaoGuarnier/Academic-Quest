package com.academicquest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.components.Util;
import com.academicquest.dto.GrupoDTO;
import com.academicquest.dto.GrupoMateriaDTO;
import com.academicquest.dto.GrupoPostDTO;
import com.academicquest.dto.GrupoUpdateDTO;
import com.academicquest.dto.UserDTO;
import com.academicquest.service.GrupoService;
import com.academicquest.service.exception.BadRequestException;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@PostMapping
	private ResponseEntity<GrupoPostDTO> save(@RequestBody GrupoPostDTO dto, BindingResult bindingResult) {
		
        String errors = Util.errorHandling(new String[]{"nome", "alunosId", "alunoLiderId", "materiaId"}, bindingResult);

		
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
		
		grupoService.save(dto);

        return ResponseEntity.ok().build();
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
	
	
	@GetMapping("/alunos/materia/{id}")
	private ResponseEntity<List<UserDTO>> getAlunosSemGrupoPorIdMateria(@PathVariable Long id) {
		List<UserDTO> users = grupoService.buscarAlunosSemGrupo(id);
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<GrupoUpdateDTO> update(@RequestBody GrupoUpdateDTO grupoDto, @PathVariable Long id) {
		GrupoUpdateDTO updateGrupo = grupoService.updateGrupo(grupoDto, id);
		return ResponseEntity.ok(updateGrupo);	
	}
}
