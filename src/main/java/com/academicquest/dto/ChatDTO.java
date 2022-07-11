package com.academicquest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.academicquest.model.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String mensagem;
	
	private LocalDateTime dataHoras;
	
	private String primeiroNome;
	
	private String segundoNome;
	
	public ChatDTO(Chat chat) {
		this.mensagem    = chat.getMensagem();
		this.dataHoras   = chat.getDataHoras();
		this.id          = chat.getId();
		this.primeiroNome = chat.getUser().getFirstName();
		this.segundoNome  = chat.getUser().getLastName();
	}
}
