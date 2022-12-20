package com.edu.rabbitMqservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.rabbitMqservice.domain.Aluno;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Optional -> serve para previnir erros
    Optional<Aluno> findByName(String name);
}
