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
public class Aluno {
	private String nome;
	@Column(unique=true)
	private String email;
	@Column(unique=true)
	private Long matricula;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}

/*
 {
 	"nome": "luan",
 	"matricula": 123456,
 	"email": "luanfelipe@gmail.com"
 }
 
 {
 	"nome": "ramon",
 	"matricula": 87654,
 	"email": "ramon@gmail.com",
     "formacao": "ciencia da compt - ufcg"
 } 
*/

/*public String nome;
@Column(unique=true)
public String email;
@Column(unique=true)
public Long matricula;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Long id;*/
