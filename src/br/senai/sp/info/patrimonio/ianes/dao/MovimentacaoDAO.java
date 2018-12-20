package br.senai.sp.info.patrimonio.ianes.dao;

import java.util.List;

import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;

public interface MovimentacaoDAO extends DAO<Movimentacao> {

	public List<Movimentacao> buscarMovimentacoesDeDeterminadoItem(Long id);
	public void deletarPorPatrimonio(Long id);
}
