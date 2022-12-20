package com.example.demo;

import javax.persistence.Column;
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
public class Professor {
	private String nome;
	private String formacao;

	@Column(unique=true)
	private String email;
	
	@Column(unique=true)
	private Long matricula;
	
	@Column(name="professorID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
