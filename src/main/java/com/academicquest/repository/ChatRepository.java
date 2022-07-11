package com.academicquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Chat;
import com.academicquest.model.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	List<User> findByUserAndMensagem(User user, String mensagem);
	
	List<Chat> findByTarefaGrupoId(Long id);
}
