package br.senai.sp.info.patrimonio.ianes.dao;

import java.util.List;

import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;

public interface ItemPatrimonioDAO extends DAO<ItemPatrimonio> {

	public List<ItemPatrimonio> buscarItensDoAmbiente(Long id);
	public List<ItemPatrimonio> buscarItensDoPatrimonio(Long id);
	public void deletarPorPatrimonio(Long id);
}
