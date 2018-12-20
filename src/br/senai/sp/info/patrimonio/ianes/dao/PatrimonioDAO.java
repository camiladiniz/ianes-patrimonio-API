package br.senai.sp.info.patrimonio.ianes.dao;

import br.senai.sp.info.patrimonio.ianes.models.Patrimonio;

public interface PatrimonioDAO extends DAO<Patrimonio>{

	public Patrimonio buscarPorNome(String nome);
	
}
