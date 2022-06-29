package com.academicquest.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academicquest.components.Util;
import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
import com.academicquest.model.User;
import com.academicquest.service.ChatService;
import com.academicquest.service.exception.BadRequestException;

@RestController
@RequestMapping("/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@PostMapping("/chatssss/{id}")
	private ResponseEntity<ChatPostDto> save(@RequestBody @Valid ChatPostDto dto, @PathVariable Long id, BindingResult bindingResult) throws ParseException, IOException {
		
        String errors = Util.errorHandling(new String[]{"mensagem"}, bindingResult);

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        
        chatService.save(dto, id);
        
        return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<ChatDto> getById(@PathVariable Long id) {
		ChatDto chatDTO = chatService.getChat(id);
		return ResponseEntity.ok(chatDTO);
	}
	
	@GetMapping
	private ResponseEntity<List<ChatDto>> getChats(User user, String mensagem) {
		List<ChatDto> chatDTO = chatService.getChats();
		return ResponseEntity.ok(chatDTO);
	}
}
