package com.academicquest.service;

import com.academicquest.dto.UserDTO;
import com.academicquest.model.User;
import com.academicquest.repository.UserRepository;
import com.academicquest.service.exception.GrupoNaoEncontradoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional(readOnly = true)
    public Page<UserDTO> buscarTodos(Pageable pageable) {
        return userRepository.findAll(pageable).map(p -> new UserDTO(p));
    }

    @Transactional(readOnly = true)
    public UserDTO buscarPorId(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        User User = optionalUser.orElseThrow(() -> new GrupoNaoEncontradoException("Entity not found"));
        UserDTO UserDTO = new UserDTO(User);
        return UserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw  new UsernameNotFoundException("User not found");
        }
        logger.info("User found: " + username);
        return user;
    }

}
