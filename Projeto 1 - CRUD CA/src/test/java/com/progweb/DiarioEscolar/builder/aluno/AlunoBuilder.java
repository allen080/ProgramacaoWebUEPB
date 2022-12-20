package com.progweb.DiarioEscolar.builder.aluno;

import com.progweb.DiarioEscolar.domain.Aluno;

import lombok.Builder;

@Builder
public class AlunoBuilder {

    @Builder.Default
    private Long id = 10L;

    @Builder.Default
    private String nome = "Joao";

    @Builder.Default
    private String matricula = "200020202";

    @Builder.Default
    private String email = "joao123@gmail.com";
   
    @Builder.Default
    private String senha = "2123";

    public Aluno toAluno() {
        return new Aluno(nome, matricula, email, senha);
    }
} 
