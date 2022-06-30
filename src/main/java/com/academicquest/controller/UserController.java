package com.academicquest.controller;

import com.academicquest.dto.UserDTO;
import com.academicquest.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService UserService;
    @GetMapping
    public ResponseEntity<Page<UserDTO>> buscarTodos(Pageable pageable) {
        Page<UserDTO> UserDTOS = UserService.buscarTodos(pageable);
        return ResponseEntity.ok().body(UserDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@PathVariable Long id) {
        UserDTO UserDTO = UserService.buscarPorId(id);
        return ResponseEntity.ok().body(UserDTO);
    }
}
