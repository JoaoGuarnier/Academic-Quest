package com.academicquest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.dashboard.DashBoardDTO;
import com.academicquest.service.DashBoardService;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@GetMapping("/{projetoId}")
	public ResponseEntity<DashBoardDTO> buscarDashBoard(@PathVariable Long projetoId){
		DashBoardDTO dashBoardDto = dashBoardService.buscarDashBoard(projetoId);
		return ResponseEntity.ok(dashBoardDto);
	}
}
