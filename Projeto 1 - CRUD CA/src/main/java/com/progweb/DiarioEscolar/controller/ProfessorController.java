package com.progweb.DiarioEscolar.controller;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.domain.dtos.ProfessorDTO;
import com.progweb.DiarioEscolar.mappers.ProfessorMapper;
import com.progweb.DiarioEscolar.services.ProfessorService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;

@RestController
@RequestMapping(value = "/professores")
@Api(value = "Professor")
public class ProfessorController {
	@Autowired
	private ProfessorService prof_service;
	@Autowired
    private ProfessorMapper profMapper;
	
	@GetMapping()
	@ApiOperation(value = "Retorna todos professores")
	public List<ProfessorDTO> listarProfessores() {
        List<Professor> professor = prof_service.ListarProfessores();
        return professor.stream().map(profMapper::convertToProfessorDTO).collect(Collectors.toList());
    }
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna professor")
	public ResponseEntity<?> buscarProfessor(@PathVariable Long id){
        return new ResponseEntity<>(profMapper.convertToProfessorDTO(prof_service.encontrarPorID(id)), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Cadastra professor")
	public ResponseEntity<ProfessorDTO> registrarProfessor(@RequestBody ProfessorDTO professorDTO) throws ExistingObjectSameNameException{
        Professor professor = profMapper.convertFromProfessorDTO(professorDTO);
		Professor novoProfessor=  prof_service.adicionarProfessor(professor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoProfessor.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza informacoes do professor")
	public ResponseEntity<ProfessorDTO> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO){
		Professor professor = profMapper.convertFromProfessorDTO(professorDTO);
        ProfessorDTO professorAtualizado =  profMapper.convertToProfessorDTO(prof_service.atualizarProfessor(id, professor));

		return ResponseEntity.ok().body(professorAtualizado);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remove um professor")
	public ResponseEntity<?> deletarProfessor(@PathVariable Long id){
		prof_service.deletarProfessor(id);
		return ResponseEntity.status(HttpStatus.OK).body("Professor Removido");

	}
}
