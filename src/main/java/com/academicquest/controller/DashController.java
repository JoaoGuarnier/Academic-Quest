package com.academicquest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.DashDTO;
import com.academicquest.service.DashService;

@RestController
@RequestMapping("/dashs")
public class DashController {
	
	@Autowired
	private DashService dashService;
	
	@GetMapping
	public ResponseEntity<DashDTO> buscaStatus() {
		
		DashDTO s = dashService.buscaStatus();
		
		return ResponseEntity.ok().body(s);
		
	}

}
