package com.academicquest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
import com.academicquest.mockDados.MockDadosDTOTest;
import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Chat;
import com.academicquest.repository.ChatRepository;
import com.academicquest.repository.TarefaGrupoRepository;

@SpringBootTest
@Transactional
public class ChatServiceTest {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository repository;
	
    @Autowired
    private TarefaGrupoRepository tarefaGrupo;
	
    private Long chatId;
    private Long notChatId;

    @BeforeEach
    public void setUpChat() throws Exception  {
    	chatId = 1l;
    	notChatId = 999L;
    }
	
	@Test
	@DisplayName("Deve salvar um ChatService.")
	public void savechatServiceExistente() throws ParseException, IOException {

		ChatPostDto chatDto = MockDadosDTOTest.createChatPostDTO();

		chatService.save(chatDto, chatId);

		assertNotNull(chatDto);
	}

	@Ignore
	@DisplayName("Retorna uma lista de Turma Mock se tiver elementos na lista")
	public void getChatAll() {
		
		repository.save(MockDadosTest.createChat());
		
		List<ChatDto> chatDto = chatService.getChats();
		
		assertThat(chatDto).isNotEmpty();
	}
	
	@Ignore
	@DisplayName("Retorna uma lista de Turma Mock se nao tiver elementos na lista")
	public void getNotChatAll() {

		List<ChatDto> chatDto = chatService.getChats();
	
		assertThat(chatDto).isNullOrEmpty();
	}
	
	@Test
	@DisplayName("Retorna uma lista de Chat se tiver elementos na lista")
	public void getNotChat() {
		
		Executable executable = () -> chatService.getChat(notChatId);
		
		Exception expectedEx = assertThrows(EntityNotFoundException.class, executable);
		
		assertEquals(expectedEx.getMessage(), "Mensagem não encontrado"); 
	}
	
	@Test
	@DisplayName("Retorna uma lista de Chat se nao tiver elementos na lista")
	public void getChat() {
		tarefaGrupo.save(MockDadosTest.createTarefaGrupo());
		Chat chat = repository.save(MockDadosTest.createChat());
		
		ChatDto a = chatService.getChat(chat.getId());
		
		assertThat(a).isNotNull();
	}
}
