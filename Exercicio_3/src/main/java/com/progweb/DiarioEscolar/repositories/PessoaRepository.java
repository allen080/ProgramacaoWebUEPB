package com.progweb.DiarioEscolar.repositories;

import com.progweb.DiarioEscolar.domain.Pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    
    Optional<Pessoa> findByNome(String nome);
}