package com.academicquest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.PessoaTestDTO;

@RestController
@RequestMapping("/teste")
public class TestController {
	
	@PostMapping
	private ResponseEntity<PessoaTestDTO> testeMethod(@RequestBody PessoaTestDTO pessoaTest) {
		
		try {
			System.out.println(pessoaTest.getNome());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(pessoaTest);
	}
}
