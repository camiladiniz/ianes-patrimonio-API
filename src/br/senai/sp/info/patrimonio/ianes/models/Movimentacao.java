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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = false)
	private Date dataMovimentacao;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, unique = false, name = "ambiente_origem")
	private Ambiente ambienteOrigem;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, unique = false, name = "ambiente_destino")
	private Ambiente ambienteDestino;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(nullable = false, unique = false, name = "usuario")
	private Usuario usuario;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, unique = false, name = "item_movimentado")	
	private ItemPatrimonio item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public Ambiente getAmbienteOrigem() {
		return ambienteOrigem;
	}

	public void setAmbienteOrigem(Ambiente ambienteOrigem) {
		this.ambienteOrigem = ambienteOrigem;
	}

	public Ambiente getAmbienteDestino() {
		return ambienteDestino;
	}

	public void setAmbienteDestino(Ambiente ambienteDestino) {
		this.ambienteDestino = ambienteDestino;
	}

	public ItemPatrimonio getItem() {
		return item;
	}

	public void setItem(ItemPatrimonio item) {
		this.item = item;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
