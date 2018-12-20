package br.senai.sp.info.patrimonio.ianes.dao;

import br.senai.sp.info.patrimonio.ianes.models.CategoriaPatrimonio;

public interface CategoriaPatrimonioDAO extends DAO<CategoriaPatrimonio>{

	public CategoriaPatrimonio buscarPorNome(String nome);
	
}
