package com.progweb.DiarioEscolar.mappers;


import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.dtos.AlunoDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AlunoMapper {

    @Autowired
    private ModelMapper modelMapper;
    
    public AlunoDTO convertToAlunoDTO(Aluno aluno) {
        AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);

        alunoDTO.setTurmas(aluno.getTurmas());
        return alunoDTO;
    }

    public Aluno convertFromAlunoDTO(AlunoDTO alunoDTO) {
        Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
        
        aluno.setTurmas(alunoDTO.getTurmas());
        return aluno;
    }
    
}
