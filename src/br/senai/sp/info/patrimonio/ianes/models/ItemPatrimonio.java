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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "itempatrimonio")
public class ItemPatrimonio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, unique = false, name = "patrimonio_id")
	private Patrimonio patrimonio;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, unique = false, name = "usuario_cadastrou_id")
	private Usuario usuario;
	
	//@ManyToOne(cascade = CascadeType.MERGE)
	@ManyToOne
	@JoinColumn(nullable = false, unique = false, name = "ambiente_id")
	private Ambiente ambiente;
	
	@Column(nullable = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataUltimaMovimentacao;
	/*private void gerarCodigo() {
		Date ano = new Date().getDate().getYear();
		String cod = ano.getYear() + patrimonio.getId() +id;
	}*/

	public Long getId() {
		return id;
	}

	public Date getDataUltimaMovimentacao() {
		return dataUltimaMovimentacao;
	}

	public void setDataUltimaMovimentacao(Date dataUltimaMovimentacao) {
		this.dataUltimaMovimentacao = dataUltimaMovimentacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	
	
}
