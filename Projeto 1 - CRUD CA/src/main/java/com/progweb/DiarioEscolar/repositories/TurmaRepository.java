package com.progweb.DiarioEscolar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.progweb.DiarioEscolar.domain.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByNome(String nome);

}
