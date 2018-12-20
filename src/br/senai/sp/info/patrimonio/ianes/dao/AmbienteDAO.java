package br.senai.sp.info.patrimonio.ianes.dao;

import br.senai.sp.info.patrimonio.ianes.models.Ambiente;

public interface AmbienteDAO extends DAO<Ambiente> {

	public Ambiente buscarPorNome(String nome);	
}
