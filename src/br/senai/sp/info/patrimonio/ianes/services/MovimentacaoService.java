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
 * Classe respons�vel por acessar o banco de dados com buscas pertinentes a movimenta��o.
 * @author Camila Diniz
 *
 */
class MovimentacaoService {

	@Autowired
	private MovimentacaoDAO movimentacaoDAO;
	
	/**
	 * Realiza a movimenta��o de determinado item
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
	 * Busca as movimenta��es realizadas por um item determinado
	 * @param itemId
	 * @return Lista de movimenta��es
	 */
	public List<Movimentacao> buscarMovimentacoesPorId(Long itemId){		
		
		return movimentacaoDAO.buscarMovimentacoesDeDeterminadoItem(itemId);
	}
	
}
