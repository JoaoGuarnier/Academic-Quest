package com.academicquest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.academicquest.model.Role;
import com.academicquest.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
