package com.progweb.DiarioEscolar.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.domain.Turma;
import com.progweb.DiarioEscolar.repositories.TurmaRepository;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

@Service
public class TurmaService {
	
	@Autowired
	private TurmaRepository repository;

    @Autowired
    private ProfessorService professorService;

	@Autowired
    private AlunoService alunoService;
	
	public Turma encontrarPorID(Long id){
        Optional<Turma> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

	public List<Turma> ListarTurmas(){
		return repository.findAll();
	}

	public Turma adicionarTurma(Turma turma)throws ExistingObjectSameNameException{
		return repository.save(turma);
	}

	public Turma buscarTurma(Long turmaID) throws NotFoundException{
		return repository.findById(turmaID).orElseThrow(() -> new NotFoundException("Não existe um turma com esse id!"));
	}

	public boolean verificarSeTurmaExiste(Long turmaID){
		return repository.existsById(turmaID);
	}

	public Turma atualizarTurma(Long id, Turma turmaRecebida){
		//pega a turma com as relacoes e so troca o nome e sala dela
		Turma turma = encontrarPorID(id);

		turma.setNome(turmaRecebida.getNome());
		turma.setSala(turmaRecebida.getSala());
		
		return repository.save(turma);
	}

	public void deletarTurma(Long turmaID){
		Turma turmaToDelete = encontrarPorID(turmaID);
        repository.delete(turmaToDelete);
	}


	
    public Turma vincularProfessor(Long idTurma, Long idProf) throws ExistingObjectSameNameException{

		//trans para dto
        Professor professor = professorService.encontrarPorID(idProf);   
		Turma turma = this.encontrarPorID(idTurma);

        turma.setProfessor(professor);
        professor.addTurma(turma);

        
        professorService.adicionarProfessor(professor);
        return adicionarTurma(turma);
    }

	public Turma matricularAluno(Long idTurma, Long idAluno) throws ExistingObjectSameNameException{

        Aluno aluno = alunoService.encontrarPorID(idAluno);
		Turma turma = this.encontrarPorID(idTurma);

		List<Aluno> novaLista = turma.getAlunos();
		novaLista.add(aluno);

		turma.setAlunos(novaLista);
    	aluno.addTurma(turma);

		alunoService.atualizarAluno(idAluno, aluno);
		return adicionarTurma(turma);
    }
}
