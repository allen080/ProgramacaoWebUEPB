package com.example.demo;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
public class TurmaController {
	@Autowired
	TurmaRepository turmaRep;
	@Autowired
	ProfessorRepository profRep;
	@Autowired
	AlunoRepository alunoRep;

	@GetMapping("/turmas")
	public List<Turma> getAllTurmas(){
		return turmaRep.findAll();
	}
	
	@GetMapping("/turmas/{id}")
	public Turma getAluno(@PathVariable Long id){
		return turmaRep.findById(id).get();
	}
	
	@PostMapping("/turmas")
	public Turma saveTurma(@RequestBody Turma turma){
		return turmaRep.save(turma);
	}
	
	@PutMapping("/turmas/{id}")
	public ResponseEntity<Turma> updateTurma(@PathVariable Long id, @RequestBody Turma turma){
		Turma novaTurma = turmaRep.findById(id).get();
		
		if(turma.getNome() != null)
			novaTurma.setNome(turma.getNome());
		if(turma.getSala() != null)
			novaTurma.setSala(turma.getSala()); 
		
		final Turma turmaAtt = turmaRep.save(novaTurma);
		return ResponseEntity.ok(turmaAtt);
	}
	
	@DeleteMapping("/turmas/{id}")
	public void deleteTurma(@PathVariable Long id){
		turmaRep.deleteById(id);
	}
	
	@PatchMapping("/turmas/{turmaId}/vincularProf/{profId}")
	public void addProfOnTurma(@PathVariable Long turmaId, @PathVariable Long profId) {
		System.out.printf("prof: %d\nturma: %d\n", profId, turmaId);
	}
	
	@GetMapping("/zuzu")
	public String soTeste(){
		try {
			System.out.println(alunoRep.getReferenceById(22l));
		} catch(EntityNotFoundException e) {
			if(e.getMessage().contains("Aluno")){
				return "[!] Aluno";
			} else if(e.getMessage().contains("Professor")){
				
			} else {
				return e.getMessage();
			}
		}
		return "ok";
	}
	
	@PatchMapping("/turmas/{turmaId}/matricularAluno/{alunoId}")
	public String matricularAlunoEmTurma(@PathVariable Long turmaId, @PathVariable Long alunoId, @RequestBody Professor prof) {
		//ProfessorRepository profRep = new ProfessorRepository();
		List<Professor> professores = profRep.findAll();
		//List<Aluno> alunos = alunoRep.findAll();
		
		//System.out.printf("alId: %d - turmaId: %d\n",alunoId,turmaId);
		
		try {
			if(alunoRep.getReferenceById(alunoId).toString() == null)
				throw new EntityNotFoundException("Aluno with id not found");
			if(turmaRep.getReferenceById(turmaId).toString() == null)
				throw new EntityNotFoundException("Professor with id not found");
		} catch(EntityNotFoundException e) {
			if(e.getMessage().contains("Aluno with")){
				return String.format("[!] Aluno de ID %l nao encontrado", alunoId);
			} else if(e.getMessage().contains("Turma with")){
				return String.format("[!] Turma de ID %l nao encontrada", turmaId);
			} else {
				return e.getMessage();
			}
		}
		
		boolean matriculado = false;
		boolean temEmail = prof.getEmail() != null;
		boolean temMatricula = prof.getEmail() != null;
		
		if(!temEmail)
			return "[!] Email do professor nao encontrada";
		else if(!temMatricula)
			return "[!] Matricula do professor nao encontrada";
		
		for(int i=0; i<professores.size(); i++){
			Professor pAtual = professores.get(i);
			
			if(pAtual.getEmail() == prof.getEmail() && pAtual.getMatricula() == prof.getMatricula()){
				Turma turmaAtt = turmaRep.findById(turmaId).get();

				turmaAtt.setIdProf(pAtual.getId());
				turmaAtt.getIdsAluno().add(alunoId);
				
				ResponseEntity.ok(turmaRep.save(turmaAtt));
				matriculado = true;
				break;
			}
		}
		
		if(!matriculado)
			return "[!] Professor nao encontrado";
		return "[*] Matriculado com sucesso";
	}
}	
