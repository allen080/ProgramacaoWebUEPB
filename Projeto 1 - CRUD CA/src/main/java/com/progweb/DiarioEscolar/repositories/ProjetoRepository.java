package com.progweb.DiarioEscolar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progweb.DiarioEscolar.domain.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{
	Optional<Projeto> findByNome(String nome);

}
