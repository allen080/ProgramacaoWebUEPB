package br.edu.uepb.example.secondmicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.uepb.example.secondmicroservice.domain.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // Optional -> serve para previnir erros
    Optional<Professor> findByName(String name);
}