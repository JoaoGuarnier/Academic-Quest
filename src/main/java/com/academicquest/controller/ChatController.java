package com.academicquest.controller;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.academicquest.components.Util;
import com.academicquest.dto.ChatPostDTO;
import com.academicquest.service.ChatService;
import com.academicquest.service.exception.BadRequestException;

@RestController
@RequestMapping("/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@PostMapping
	private ResponseEntity<ChatPostDTO> save(@RequestBody @Valid ChatPostDTO dto, BindingResult bindingResult) throws ParseException, IOException {
		
        String errors = Util.errorHandling(new String[]{"mensagem", "userId", "tarefaGrupoId" }, bindingResult);

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        
        ChatPostDTO chatDto = chatService.save(dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{mensagem}")
                .buildAndExpand(chatDto.getMensagem()).toUri();
        
        return  ResponseEntity.created(uri).body(chatDto);
	}
}
