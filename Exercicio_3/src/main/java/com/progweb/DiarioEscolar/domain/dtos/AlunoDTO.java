package com.progweb.DiarioEscolar.domain.dtos;

import java.util.List;

import com.progweb.DiarioEscolar.domain.Projeto;
import com.progweb.DiarioEscolar.domain.Turma;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO {
    
    public AlunoDTO(String nome2, String matricula2, String email2, String senha2) {
    }
    private Long id;
    private String nome;
    private String matricula;
    private String email;
    private String senha;
    private String papelProjeto;
    private List<Turma> turmas;
    private Projeto projeto;
    
    
}