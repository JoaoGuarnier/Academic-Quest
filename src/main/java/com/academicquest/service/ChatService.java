package com.academicquest.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academicquest.dto.ChatPostDTO;
import com.academicquest.model.Chat;
import com.academicquest.model.TarefaGrupo;
import com.academicquest.model.User;
import com.academicquest.repository.ChatRepository;
import com.academicquest.repository.TarefaGrupoRepository;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.TarefaGrupoNaoEncontradoException;
import com.academicquest.service.exception.UsuarioNaoEncontradoException;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TarefaGrupoRepository tarefaGrupo;
    
	@Transactional
	public ChatPostDTO save(ChatPostDTO dto) throws ParseException, IOException {
		
		Chat chat = new Chat();
		User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));
		TarefaGrupo tarefa = tarefaGrupo.findById(dto.getTarefaGrupoId()).orElseThrow(() -> new TarefaGrupoNaoEncontradoException("Tarefa grupo nao encontrado"));

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
}
