package com.academicquest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academicquest.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
