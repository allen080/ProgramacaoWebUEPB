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
public class AlunoController {
	
	@Autowired
	AlunoRepository alunoRep;

	@GetMapping("/alunos")
	public List<Aluno> getAllAlunos(){
		//List<Aluno> l = new ArrayList<Aluno>();
		return alunoRep.findAll();
	}
	
	@GetMapping("/alunos/{id}")
	public Aluno getAluno(@PathVariable Long id){
		System.out.println(id);
		
		return alunoRep.findById(id).get();
	}
	
	@PostMapping("/alunos")
	public Aluno saveAluno(@RequestBody Aluno al){
		return alunoRep.save(al);
	}
	
	@PutMapping("/alunos/{id}")
	public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno al){
		Aluno novoAl = alunoRep.findById(id).get();
		
		if(al.getNome() != null)
			novoAl.setNome(al.getNome());
		if(al.getMatricula() != null)
			novoAl.setMatricula(al.getMatricula()); 
		if(al.getEmail() != null)
			novoAl.setEmail(al.getEmail());         

		final Aluno alunoAtualizado = alunoRep.save(novoAl);
		return ResponseEntity.ok(alunoAtualizado);
	}
	
	@DeleteMapping("/alunos/{id}")
	public void deleteAluno(@PathVariable Long id){
		alunoRep.deleteById(id);
	}
}	
