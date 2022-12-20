package com.progweb.DiarioEscolar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

import com.progweb.DiarioEscolar.domain.Turma;
import com.progweb.DiarioEscolar.domain.dtos.TurmaDTO;
import com.progweb.DiarioEscolar.mappers.TurmaMapper;

import com.progweb.DiarioEscolar.services.TurmaService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;

@RestController
@RequestMapping(value = "/turmas")
@Api(value = "Turma")
public class TurmaController {
	
	@Autowired
	private TurmaService service;
	
	@Autowired
    private TurmaMapper turmaMapper;
	
	@PreAuthorize("hasAnyRole('PROF')")
	@GetMapping()
	@ApiOperation(value = "Retorna a lista de todas as turmas cadastrados.")
	public List<TurmaDTO> listarTurmas() {
        List<Turma> turmas = service.ListarTurmas();
        return turmas.stream()
                        .map(turmaMapper::convertToTurmaDTO)
                        .collect(Collectors.toList());
    }
	
	@PostMapping
	@ApiOperation(value = "Cadastra uma nova turma.")
	public ResponseEntity<?> registrarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
            return new ResponseEntity<>(service.adicionarTurma(turma), HttpStatus.CREATED);
        } catch (ExistingObjectSameNameException e) {
			return ResponseEntity.badRequest().body("Nome da turma ja Registrada");
        }
    }

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna uma turma pelo seu {id}.")
	public ResponseEntity<?> buscarTurma(@PathVariable Long id) {

        return new ResponseEntity<>(turmaMapper.convertToTurmaDTO(service.encontrarPorID(id)), HttpStatus.OK);
        
    }

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza os dados de uma turma.")
	public TurmaDTO atualizarTurma(@PathVariable("id") Long id, @RequestBody TurmaDTO turmaDTO) {
        Turma turma = turmaMapper.convertFromTurmaDTO(turmaDTO);
        return turmaMapper.convertToTurmaDTO(service.atualizarTurma(id, turma));
    }

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta uma turma Cadastrado.")
	public ResponseEntity<Object> deletarTurma(@PathVariable("id") Long id){

		service.deletarTurma(id);
		return ResponseEntity.status(HttpStatus.OK).body("Turma Deletada com sucesso");

	}

    //PATCH
	@PreAuthorize("hasAnyRole('PROF')")
	@PatchMapping("/{idTurma}/matricularAluno/{idAluno}")
	@ApiOperation(value = "Matricular um aluno a uma turma.")
    public TurmaDTO matricularAluno(@PathVariable("idTurma") Long idTurma,@PathVariable("idAluno") Long idAluno) throws ExistingObjectSameNameException, NotFoundException{

        return turmaMapper.convertToTurmaDTO(service.matricularAluno(idTurma, idAluno));

    }

	@PreAuthorize("hasAnyRole('PROF')")
	@PatchMapping("/{idTurma}/vincularProfessor/{idProf}")
	@ApiOperation(value = "Vincula um Professor a uma turma.")
    public TurmaDTO vincularProfessor(@PathVariable("idTurma") Long idTurma,@PathVariable("idProf") Long idProf) throws ExistingObjectSameNameException, NotFoundException{
        return turmaMapper.convertToTurmaDTO(service.vincularProfessor(idTurma, idProf));
	
	}


	

}
