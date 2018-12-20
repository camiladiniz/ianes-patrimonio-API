package br.senai.sp.info.patrimonio.ianes.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sp.info.patrimonio.ianes.dao.PatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.models.Patrimonio;
import br.senai.sp.info.patrimonio.ianes.utils.ConverterBase64;

@Service
/**
 * L�gica da regra de neg�cios dos patrim�nios.
 * @author Camila Diniz
 *
 */
public class PatrimonioService {

	@Autowired
	private PatrimonioDAO patrimonioDAO;
	
	@Autowired
	private ConverterBase64 base64;
	
	/**
	 * Busca todos os patrim�nios
	 * @return
	 */
	public List<Patrimonio> buscarTodos(){
		return patrimonioDAO.buscarTodos();
	}
	
	/**
	 * Busca um patrim�nio espec�fico atrav�s de seu id
	 * @param id
	 * @return - Patrim�nio buscado
	 * @throws EntidadeNaoEncontrada
	 * @throws IOException 
	 */
	public Patrimonio buscarPorId(Long id) throws EntidadeNaoEncontrada, IOException {
		
		Patrimonio patrimonio = patrimonioDAO.buscar(id);
		if(patrimonio == null) {
			throw new EntidadeNaoEncontrada();
		}
		
		System.out.println(patrimonio.getCaminhoImagem());
		//Convertendo o caminho da foto
			if(patrimonio.getCaminhoImagem() != null) {
				System.out.println("entrou no if");
					base64.getFotoBase64(patrimonio.getCaminhoImagem());
					System.out.println("imagem em bytes" + base64.getFotoBase64(patrimonio.getCaminhoImagem()));
				}
		
		return patrimonio;
		
	}
	
}
