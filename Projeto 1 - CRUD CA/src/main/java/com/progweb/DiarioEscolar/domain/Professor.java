package com.progweb.DiarioEscolar.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "professor")
public class Professor extends Pessoa{
	private static final long serialVersionUID = 1L;
	@Column(name="formacao")
	private String formacao;
	@OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Turma> turmas = new ArrayList<>();
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name = "projetoProf_id")
	private Projeto projeto;

	public void addTurma(Turma turma){
		this.turmas.add(turma);
	}

	public Professor(String nome, String matricula,String formacao, String email, String senha){
		this.nome = nome;
		this.matricula = matricula;
		this.formacao = formacao;
		this.email = email;
		this.senha = senha;
    }
}
