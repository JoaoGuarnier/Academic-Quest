package com.academicquest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
