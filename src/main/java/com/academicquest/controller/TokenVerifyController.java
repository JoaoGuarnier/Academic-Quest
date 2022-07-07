package com.academicquest.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verificar/token")
public class TokenVerifyController {

    @Value("${jwt.secret}")
    private String tokenSenha;

    @GetMapping("/{token}")
    private ResponseEntity validarToken(@PathVariable String token) {
        try {
            Jwts.parser().setSigningKey(tokenSenha.getBytes()).parseClaimsJws(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
