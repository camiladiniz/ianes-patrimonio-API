package br.senai.sp.info.patrimonio.ianes.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "patrimonio")
public class Patrimonio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 40, nullable = false, unique = true)
	@NotNull
	@Size(min = 1, max = 40)
	private String nome;
	
	@Column(nullable = false, unique = false)
	private Date dataCadastro;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, name = "categoria")
	//@NotNull
	private CategoriaPatrimonio categoria;
	
	//Mais de um patrimônio cadastrado por um usuário
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, name = "usuario_cadastrou")
	private Usuario usuarioCadastrou;
	
	@Transient
	private String caminhoImagem;

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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public CategoriaPatrimonio getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaPatrimonio categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuarioCadastrou() {
		return usuarioCadastrou;
	}

	public void setUsuarioCadastrou(Usuario usuarioCadastrou) {
		this.usuarioCadastrou = usuarioCadastrou;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}
	
	
	
	
}
