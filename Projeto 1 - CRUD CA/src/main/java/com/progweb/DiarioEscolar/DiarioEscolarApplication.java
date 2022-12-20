package com.progweb.DiarioEscolar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.ProfessorService;

@SpringBootApplication
public class DiarioEscolarApplication implements CommandLineRunner{
	@Autowired
	private AlunoService alunoServ;
	@Autowired
	private ProfessorService professorServ; 
	
	public static void main(String[] args) {
		SpringApplication.run(DiarioEscolarApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Exemplo de criacao de Aluno
		Aluno aluno1 = new Aluno("luan","172810175", "luan08@gmail.com","123");
		// Exemplo de criacao de Professor
		Professor prof1 = new Professor("ramon","182567941", "ProgramacaoWeb","ramon@gmail.com","456");
		
		alunoServ.adicionarAluno(aluno1);
		professorServ.adicionarProfessor(prof1);
	}
}
