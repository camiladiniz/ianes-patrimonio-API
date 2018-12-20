package br.senai.sp.info.patrimonio.ianes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.exceptions.ValidacaoException;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;

@Service
/**
 * Classe responsável por acessar o banco de dados com buscas pertinentes a movimentação.
 * @author Camila Diniz
 *
 */
class MovimentacaoService {

	@Autowired
	private MovimentacaoDAO movimentacaoDAO;
	
	/**
	 * Realiza a movimentação de determinado item
	 * @param movimentacao
	 * @param br
	 * @return Movimentacao
	 * @throws ValidacaoException
	 */
	public Movimentacao cadastrar(Movimentacao movimentacao, BindingResult br) throws ValidacaoException {
		
		if(br.hasErrors()) {
			throw new ValidacaoException();
		}
		
		movimentacaoDAO.persistir(movimentacao);
		
		return movimentacao;
	}
	
	/**
	 * Busca as movimentações realizadas por um item determinado
	 * @param itemId
	 * @return Lista de movimentações
	 */
	public List<Movimentacao> buscarMovimentacoesPorId(Long itemId){		
		
		return movimentacaoDAO.buscarMovimentacoesDeDeterminadoItem(itemId);
	}
	
}
