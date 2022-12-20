package com.example.demo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Turma {
	private String nome;
	private String sala;
	private Long idProf;
	//private List<Long> idsAluno;
	private LinkedList<Long> idsAluno;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
