package br.senai.sp.info.patrimonio.ianes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sp.info.patrimonio.ianes.dao.AmbienteDAO;
import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.models.Ambiente;

@Service
/**
 * Lógica de negócios do ambiente e suas respectivas buscas ao banco de dados
 * @author Camila Diniz
 *
 */
public class AmbienteService {

	@Autowired
	private AmbienteDAO ambienteDAO;
	
	/**
	 * Busca todos os ambientes cadastrados
	 * @return Lista de ambientes
	 */
	public List<Ambiente> buscarTodos(){
		return ambienteDAO.buscarTodos();
	}
	
	/**
	 * Busca o ambiente através de seu id
	 * @param id
	 * @return Ambiente
	 * @throws EntidadeNaoEncontrada
	 */
	protected Ambiente buscarPorId(Long id) throws EntidadeNaoEncontrada {
		
		Ambiente ambiente = ambienteDAO.buscar(id);
		if(ambiente == null) {
			throw new EntidadeNaoEncontrada();
		}
		
		return ambiente;
	}
	
}
