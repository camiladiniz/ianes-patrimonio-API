package br.senai.sp.info.patrimonio.ianes.ws.rest.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;

import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.exceptions.ValidacaoException;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;
import br.senai.sp.info.patrimonio.ianes.services.UsuarioService;
import br.senai.sp.info.patrimonio.ianes.utils.JwtUtils;
import br.senai.sp.info.patrimonio.ianes.utils.MapUtils;

@RestController
@RequestMapping("/rest/auth")
/**
 * Recebe e envia as requisições relacionadas a autenticação ao service
 * @author Camila Diniz
 *
 */
public class AuthRestController {
	
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Gera token a partir de um usuário válido
	 * @param usuario
	 * @param bindingResult
	 * @return - Token
	 * @throws IllegalArgumentException
	 * @throws JWTCreationException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/jwt")
	public ResponseEntity<Object> gerarTokenJwt(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException{
		
		try {
			
			Usuario usuarioBuscado = usuarioService.buscarPorEMailESenha(usuario, bindingResult);
			
			Map<String, String> mapaDoToken = new HashMap<>();
			mapaDoToken.put("token", JwtUtils.gerarToken(usuarioBuscado));
			return ResponseEntity.ok(mapaDoToken);
			
		} catch (ValidacaoException e) {
			return ResponseEntity.unprocessableEntity().body(MapUtils.mapaDe(bindingResult));
		} catch (EntidadeNaoEncontrada e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		
		
		
	}
	
}
