package com.academicquest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.academicquest.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
    
}
