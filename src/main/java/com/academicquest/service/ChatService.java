package com.academicquest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatDto;
import com.academicquest.dto.ChatPostDto;
import com.academicquest.model.Chat;
import com.academicquest.model.User;
import com.academicquest.repository.ChatRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Transactional
	public void save(ChatPostDto dto) throws ParseException {
		
		Chat chat = dto.convertDTOToEntity();
//		Chat chat = convertToEntity(dto);
		
		chatRepository.save(chat);
	}

	@Transactional(readOnly = true)
	public ChatDto getChat(Long id) {
		
		ChatDto chatDTO = new ChatDto();
		
		Optional<Chat> chatOptional = chatRepository.findById(id);
		Chat chat = chatOptional.orElseThrow(() -> new EntityNotFoundException("Mensagem não encontrado"));
		
		chatDTO.setId       (chat.getId());
		chatDTO.setMensagem (chat.getMensagem());
		chatDTO.setDataHoras(chat.getDataHoras());
		chatDTO.setUser		(chat.getUser());
		
		return chatDTO;
	}
	
	@Transactional(readOnly = true)
	public List<ChatDto> getChats() {
		
		
		return chatRepository.findAll(Sort.by(Sort.Direction.ASC, "dataHoras")).stream().map(ChatDto::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<ChatDto> getUserChat() {
		
		return chatRepository.findAll().stream().map(ChatDto::new).collect(Collectors.toList());
	}
	
	private Chat convertToEntity(ChatPostDto dto) throws ParseException {
		
		Chat chat = new Chat();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
		
		
		
		LocalDateTime now = LocalDateTime.now();
		
		String formatDateTime = now.format(formatter);
		Locale locale = new Locale("pt", "BR");

		
		LocalDateTime formatDateTime2 = LocalDateTime.parse(formatDateTime, DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss", locale));
		
//		LocalDateTime formatDateTime3 = formatDateTime;
//		dto.setDataHoras(formatDateTime);
		
		
		System.out.println("teste1111 : " + now);
		System.out.println("teste2222 : " + formatDateTime);
		System.out.println("teste3333 : " + formatDateTime2);
//		System.out.println("teste3333 : " + );
		System.out.println("teste3333 : " + dto.getDataHoras());
		
		
		
		chat.setDataHoras(dto.getDataHoras());
		chat.setMensagem (dto.getMensagem());
		chat.setUser     (dto.getUser());
		
		return chat;
	} 
	
}
