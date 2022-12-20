package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/")
public class ProfessorController {
	
	@Autowired
	ProfessorRepository profRep;

	@GetMapping("/professores")
	public List<Professor> getAllProfs(){
		return profRep.findAll();
	}
	
	@GetMapping("/professores/{id}")
	public Professor getProf(@PathVariable Long id){
		return profRep.findById(id).get();
	}
	
	@PostMapping("/professores")
	public Professor saveProf(@RequestBody Professor prof){
		return profRep.save(prof);
	}
	
	@PutMapping("/professores/{id}")
	public ResponseEntity<Professor> updateProf(@PathVariable Long id, @RequestBody Professor prof){
		Professor novoProf = profRep.findById(id).get();
		
		if(prof.getNome() != null)
			novoProf.setNome(prof.getNome());
		if(prof.getMatricula() != null)
			novoProf.setMatricula(prof.getMatricula()); 
		if(prof.getEmail() != null)
			novoProf.setEmail(prof.getEmail());
		if(prof.getFormacao() != null)
			novoProf.setFormacao(prof.getFormacao());   

		final Professor profAtualizado = profRep.save(novoProf);
		return ResponseEntity.ok(profAtualizado);
	}
	
	@DeleteMapping("/professores/{id}")
	public void deleteProf(@PathVariable Long id){
		profRep.deleteById(id);
	}
}	
