package br.edu.uepb.example.alunoconsumer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.uepb.example.alunoconsumer.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
        
	Optional<Aluno> findByName(String name);
}
