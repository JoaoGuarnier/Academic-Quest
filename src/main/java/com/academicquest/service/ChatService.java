package com.academicquest.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
import com.academicquest.model.Chat;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.User;
import com.academicquest.repository.ChatRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import com.academicquest.repository.UserRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TarefaGrupoRepository tarefaGrupo;
    
    @Autowired
    private ModelMapper modelMapper;
	
	@Transactional
	public ChatPostDto save(ChatPostDto dto) throws ParseException, IOException {
		
		
		Chat chat = new Chat();
		User user = userRepository.getById(dto.getUserId());
		TarefaGrupo tarefa = tarefaGrupo.getById(dto.getTarefaGrupoId());

		dto.setDataHoras(LocalDateTime.now());
		dto.setUserId(user.getId());
		dto.setTarefaGrupoId(tarefa.getId());
		
		chat.setDataHoras(dto.getDataHoras());
		chat.setMensagem (dto.getMensagem());
		chat.setTarefaGrupo(tarefa);
		chat.setUser(user);
		chatRepository.save(chat);
		return dto;
	}

	@Transactional(readOnly = true)
	public ChatDto getChat(Long id) {
		
		ChatDto chatDTO = new ChatDto();
		
		Optional<Chat> chatOptional = chatRepository.findById(id);
		Chat chat = chatOptional.orElseThrow(() -> new EntityNotFoundException("Mensagem não encontrado"));
//		ChatDto chatDTO = modelMapper.map(chat, ChatDto.class);
		chatDTO.setId       (chat.getId());
		chatDTO.setMensagem (chat.getMensagem());
		chatDTO.setDataHoras(chat.getDataHoras());
//		chatDTO.setUser		(chat.getUser());
		
		return chatDTO;
	}
	
	@Transactional(readOnly = true)
	public List<ChatDto> getChats() {
		
		List<Chat> chat = chatRepository.findAll();
//		List<ChatDto> tetste = chat.stream().map(user -> modelMapper.map(user, ChatDto.class)).collect(Collectors.toList());
		List<ChatDto> tetste = chat.stream().map(ChatDto::new).collect(Collectors.toList());

		return tetste;
	}
	
//	@Transactional
//	private Chat convertToEntity(ChatPostDto dto, Long id) throws ParseException, IOException {
//
//;
//		return chat;
//	} 
}
