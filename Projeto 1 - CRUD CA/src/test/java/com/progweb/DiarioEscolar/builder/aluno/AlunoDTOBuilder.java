package com.progweb.DiarioEscolar.builder.aluno;

import com.progweb.DiarioEscolar.domain.dtos.AlunoDTO;

import lombok.Builder;

@Builder
public class AlunoDTOBuilder {
    
    @Builder.Default
    private String nome = "Joao";

    @Builder.Default
    private String matricula = "200020202";

    @Builder.Default
    private String email = "joao123@gmail.com";
   
    @Builder.Default
    private String senha = "2123";
    
    public AlunoDTO toAlunoDTO() {
        return new AlunoDTO(nome, matricula, email, senha);
    }
}
