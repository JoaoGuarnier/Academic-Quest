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

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
import com.academicquest.model.User;
import com.academicquest.service.ChatService;

@RestController
@RequestMapping("/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@PostMapping
	private ResponseEntity<ChatPostDto> save(@RequestBody ChatPostDto dto) {
		chatService.save(dto);
        return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<ChatDto> getById(@PathVariable Long id) {
		ChatDto chatDTO = chatService.getChat(id);
		return ResponseEntity.ok(chatDTO);
	}
	
	@GetMapping
	private ResponseEntity<List<ChatDto>> getChats(User user, String mensagem) {
		List<ChatDto> chatDTO = chatService.getChats(user, mensagem);
		return ResponseEntity.ok(chatDTO);
	}
}
