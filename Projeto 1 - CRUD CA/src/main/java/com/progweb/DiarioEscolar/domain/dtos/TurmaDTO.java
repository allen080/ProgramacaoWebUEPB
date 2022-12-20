package com.progweb.DiarioEscolar.domain.dtos;

import java.util.List;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.Professor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TurmaDTO {
    
    private Long id;
    private String nome;
	private String sala;
    private List<Aluno> alunos;
    private Professor professor;

}