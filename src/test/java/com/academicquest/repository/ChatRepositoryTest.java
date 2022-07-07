package com.academicquest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.academicquest.mockDados.MockDadosTest;
import com.academicquest.model.Chat;

@DataJpaTest
public class ChatRepositoryTest {

	@Autowired
	private ChatRepository repository;
	
    private Long chatId;
    private Long notChatId;

    @BeforeEach
    public void setUpChat() throws Exception  {
    	chatId = 1L;
    	notChatId = 999L;
    }
    
    @Test
    @DisplayName("Deve deleta uma Chat quando tiver no banco")
    public void deleteChatExistir() {
    	
    	Chat chatTest = repository.save(MockDadosTest.createChat());
    	
    	repository.deleteById(chatTest.getId());
    	
    	Optional<Chat> chat = repository.findById(chatId);
    	
    	assertFalse(chat.isPresent());
    }
    
    @Test
    @DisplayName("Deve salvar um Chat.")
    public void saveChat() {
    	
    	Chat chatTest = repository.save(MockDadosTest.createChat());
    	
        assertNotNull(chatTest);
    }
    
    @Test
    @DisplayName("Deve lanca uma exception quando o valor nao existir o resultado no banco")
    public void deletaChatNaoExiste() {
    	
        assertThrows(EmptyResultDataAccessException.class,() -> {
        	repository.deleteById(notChatId);
        });
    }

    @Test
    @DisplayName("Deve retorna um False se o id, nao existe no banco")
    public void testarFindByIdNaoExistente() {
    	
        Optional<Chat> optionalChat = repository.findById(notChatId);
        assertFalse(optionalChat.isPresent());
    }

    @Test
    @DisplayName("Deve retorna um True se o id, existe no banco")
    
    public void testarFindByIdExistente() {
    	
    	Chat chat = repository.save(MockDadosTest.createChat());
    	
    	Optional<Chat> optionalChat = repository.findById(chat.getId());
    	
    	assertTrue(optionalChat.isPresent());
    }
    
    @Test
    @DisplayName("Se a lista tiver elemento, retorna um true, e se o id existe no banco")
    public void findAllChat() {
    	
    	repository.save(MockDadosTest.createChat());
    	
    	List<Chat> optionalChat = repository.findAll();
    	assertThat(optionalChat).isNotEmpty();
    }
    
    @Test
    @DisplayName("Se a lista estiver vazia ou nula, deve retorna um False")
    public void findAllNotChat() {
    	
    	List<Chat> optionalChat = repository.findAll();
    	assertThat(optionalChat).isNullOrEmpty();;
    }
}
