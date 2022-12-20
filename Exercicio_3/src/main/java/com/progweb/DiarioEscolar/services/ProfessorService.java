package com.progweb.DiarioEscolar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.domain.enums.Authority;
import com.progweb.DiarioEscolar.repositories.ProfessorRepository;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository repository;
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	public Professor encontrarPorID(Long id){
		Optional<Professor> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

	public List<Professor> ListarProfessores (){
		return repository.findAll();
	}

	public Professor adicionarProfessor(Professor professor) throws ExistingObjectSameNameException{
		
		professor.setSenha(encoder.encode(professor.getSenha()));

		professor.addAuthority(Authority.PROF);
		return repository.save(professor);
	}


	public Professor atualizarProfessor(Long id, Professor professor){
		professor.setId(id);
        return repository.save(professor);
		
	}

	public void deletarProfessor(Long id){
		Professor professor = encontrarPorID(id);
        repository.deleteById(professor.getId());
	}

}
