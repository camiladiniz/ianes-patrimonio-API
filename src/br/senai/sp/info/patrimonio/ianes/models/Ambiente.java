package br.senai.sp.info.patrimonio.ianes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ambiente")
public class Ambiente {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 40, nullable = false, unique = true)
	@NotNull
	@Size(min = 1, max = 40)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
