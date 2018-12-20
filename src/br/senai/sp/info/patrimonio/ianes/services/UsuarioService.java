package br.senai.sp.info.patrimonio.ianes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.senai.sp.info.patrimonio.ianes.dao.UsuarioDAO;
import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.exceptions.ValidacaoException;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Service
/**
 * Lógica da regra de negócios, constrói os models e acessa o banco  de dados.
 * @author Camila Diniz
 *
 */
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/**
	 * Valida o usuário que está sendo autenticado e faz uma busca para testar a veracidade dos dados enviados.
	 * @param usuario
	 * @param bindingResult
	 * @return - Usuario
	 * @throws ValidacaoException
	 * @throws EntidadeNaoEncontrada
	 */
	public Usuario buscarPorEMailESenha(Usuario usuario, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontrada {
		
		if(bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("senha")) {
			throw new ValidacaoException();
		}
		
		usuario.hashearSenha();
		Usuario usuarioBuscado = usuarioDAO.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());
		
		if(usuarioBuscado == null) {
		throw new EntidadeNaoEncontrada();
		}
		
		return usuarioBuscado;
	}
	
}
