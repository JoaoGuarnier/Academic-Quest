package com.academicquest.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatPostDTO;
import com.academicquest.mockDados.MockDadosDTOTest;

@SpringBootTest
@Transactional
public class ChatServiceTest {

	@Autowired
	private ChatService chatService;
	
	@Test
	@DisplayName("Deve salvar um ChatService.")
	public void savechatServiceExistente() throws ParseException, IOException {

		ChatPostDTO chatDto = MockDadosDTOTest.createChatPostDTO();

		chatService.save(chatDto);

		assertNotNull(chatDto);
	}
}
