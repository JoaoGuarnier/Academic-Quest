package com.academicquest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.academicquest.model.Chat;
import com.academicquest.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String mensagem;
	
	private LocalDateTime dataHoras;
	
	private User user;
	
	public ChatDto(Chat chat) {
		this.mensagem  = chat.getMensagem();
		this.dataHoras = chat.getDataHoras();
		this.id        = chat.getId();
		this.user      = chat.getUser();
	}
}
