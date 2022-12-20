package com.progweb.DiarioEscolar.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa{
	private static final long serialVersionUID = 1L;
	@Column
	private String papelProjeto;
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(name = "aluno_turma",joinColumns = @JoinColumn(name = "aluno_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "turma_id", referencedColumnName = "id"))
	private List<Turma> turmas = new ArrayList<>();

	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "projeto_id", referencedColumnName = "id")
    private Projeto projeto;
	
	public void addTurma(Turma turma){
		this.turmas.add(turma);
	}
    public Aluno(String nome, String matricula, String email, String senha){
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
		this.senha = senha;
    }

}
