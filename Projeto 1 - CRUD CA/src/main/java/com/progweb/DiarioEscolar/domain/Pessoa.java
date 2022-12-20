package com.progweb.DiarioEscolar.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.progweb.DiarioEscolar.domain.enums.Authority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Pessoa")
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	protected String nome;

	@Column(unique = true)
	protected String matricula;
	@Column( unique = true)
	protected String email;
	protected String senha;

	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITY")
	protected Set<Integer> authority= new HashSet<>();

	public Set<Authority> getAuthority() {
        return authority.stream().map(x -> Authority.toEnum(x)).collect(Collectors.toSet());
    }
    public void addAuthority(Authority authority) {
        this.authority.add(authority.getCodigo());
    }
}
