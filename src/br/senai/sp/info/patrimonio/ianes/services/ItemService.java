package br.senai.sp.info.patrimonio.ianes.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.exceptions.ValidacaoException;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;
import br.senai.sp.info.patrimonio.ianes.utils.ConverterBase64;

@Service
/**
 * L�gica da regra de neg�cios dos itens de patrim�nio.
 * @author Camila Diniz
 *
 */
public class ItemService {

	@Autowired
	private ItemPatrimonioDAO itemDAO;
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Autowired
	private AmbienteService ambienteService;
	
	/**
	 * Busca todos os itens cadastrados
	 * @return Lista de itens
	 */
	public List<ItemPatrimonio> buscarTodos(){
		return itemDAO.buscarTodos();
	}
	
	/**
	 * Busca um item espec�fico atrav�s de seu id
	 * @param id
	 * @return - Item
	 * @throws EntidadeNaoEncontrada
	 * @throws IOException 
	 */
	public ItemPatrimonio buscarPorId(Long id) throws EntidadeNaoEncontrada, IOException {
		
		ItemPatrimonio item = itemDAO.buscar(id);
		if(item == null) {
			throw new EntidadeNaoEncontrada();
		}
			
		return item;
	}
	
	/**
	 * Busca todas as movimenta��es de um determinado item
	 * @param id
	 * @return - Lista de Movimenta��es
	 * @throws EntidadeNaoEncontrada
	 * @throws IOException 
	 */
	public List<Movimentacao> buscarMovimentacoesPorId (Long id) throws EntidadeNaoEncontrada, IOException{
		
		return movimentacaoService.buscarMovimentacoesPorId(buscarPorId(id).getId());
		
	}
	
	/**
	 * Realiza a movimenta��o de determinado item
	 * @param idItem
	 * @param movimentacao
	 * @param br
	 * @return Movimentacao
	 * @throws EntidadeNaoEncontrada
	 * @throws ValidacaoException
	 * @throws IOException 
	 */
	public Movimentacao movimentarItem(Long idItem, Movimentacao movimentacao, BindingResult br) throws EntidadeNaoEncontrada, ValidacaoException{
		
		//Verificando se o item j� se encontra no ambiente destino da movimenta��o
		ItemPatrimonio itemASerMovimentado = itemDAO.buscar(idItem);
		
		//Verificando se o item que est� sendo movimentado existe			
		if(itemASerMovimentado == null) {
			br.addError(new FieldError("movimentacao", "item", "O item n�o existe!"));
			throw new EntidadeNaoEncontrada();
		}
		
		if(movimentacao.getAmbienteDestino() == null) {
			br.addError(new FieldError("movimentacao", "item", "� necess�rio informar um ambiente como destino!"));
			throw new EntidadeNaoEncontrada();
		}
		
		//Verifica se o par�metro ambienteDestino foi informado
		if (movimentacao.getAmbienteDestino().getId() == null) {
			br.addError(new FieldError("movimentacao", "ambienteDestino", "Destino est� vazio"));
			throw new EntidadeNaoEncontrada();
		}
		
		//Verifica se o ambiente informado existe
		if(ambienteService.buscarPorId(movimentacao.getAmbienteDestino().getId()) == null) {
			br.addError(new FieldError("movimentacao", "ambienteDestino", "Ambiente de destino inv�lido"));
			throw new EntidadeNaoEncontrada();
		}
		
		//Verificando se o item n�o se encontra no ambiente selecionado como destino
		if(itemASerMovimentado.getAmbiente().getId() == movimentacao.getAmbienteDestino().getId()) {
			br.addError(new FieldError("movimentacao", "ambienteDestino", "O item j� se encontra neste ambiente!"));
			throw new ValidacaoException();
		}
		
		if(br.hasErrors()) {
			throw new ValidacaoException();
		}
		
			
		movimentacao.setAmbienteOrigem(itemASerMovimentado.getAmbiente());
		movimentacao.setDataMovimentacao(new Date());
		
		//Pegando o usu�rio logado
		Authentication usuarioLogado = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		movimentacao.setUsuario((Usuario) usuarioLogado);
		
		movimentacao.setItem(itemASerMovimentado);
		movimentacaoService.cadastrar(movimentacao, br);
		
		itemASerMovimentado.setAmbiente(movimentacao.getAmbienteDestino());
		itemASerMovimentado.setDataUltimaMovimentacao(movimentacao.getDataMovimentacao());
		
		itemDAO.alterar(itemASerMovimentado);
		
		return movimentacao;
		
	}
	
}
