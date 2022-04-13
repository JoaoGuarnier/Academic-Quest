package com.academicquest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.dto.PessoaTestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/teste")
public class TestController {
	
	@PostMapping
	private ResponseEntity<String> testeMethod(@RequestBody PessoaTestDto pessoaTest) {
		
		try {
			System.out.println(pessoaTest.getNome());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body("Funcionou!");
		
		
	}
	
	

}
