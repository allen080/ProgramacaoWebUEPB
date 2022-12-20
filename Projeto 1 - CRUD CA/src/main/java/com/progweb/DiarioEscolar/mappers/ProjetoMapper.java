package com.progweb.DiarioEscolar.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progweb.DiarioEscolar.domain.Projeto;
import com.progweb.DiarioEscolar.domain.dtos.ProjetoDTO;

@Component
public class ProjetoMapper {
	
	   @Autowired
	    private ModelMapper modelMapper;
	    
	    public ProjetoDTO convertToProjetoDTO(Projeto projeto) {
	        ProjetoDTO projetoDTO = modelMapper.map(projeto, ProjetoDTO.class);

	        return projetoDTO;
	    }

	    public Projeto convertFromProjetoDTO(ProjetoDTO projetoDTO) {
	        Projeto projeto = modelMapper.map(projetoDTO, Projeto.class);
	    
	        return projeto;
	    }

}
