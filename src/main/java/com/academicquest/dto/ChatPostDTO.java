package com.academicquest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatPostDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="O mensagem nao pode ser ser nulo, tem que ser preenchido")
	private String mensagem;
	
	private LocalDateTime dataHoras;
	
	@NotNull(message = "por favor adicionar o id do user")
	private Long userId;
	
	@NotNull(message = "por favor adicionar o id da tarefa dos grupo")
	private Long tarefaGrupoId;
}