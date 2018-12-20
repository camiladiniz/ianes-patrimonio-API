package br.senai.sp.info.patrimonio.ianes.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.info.patrimonio.ianes.models.TipoUsuario;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

/**
 * Geração e tratativas do token (chave de acesso)
 * @author Camila Diniz
 *
 */
public class JwtUtils {

	public static final String TOKEN_AUTH_CHAVE_PRIVADA = "C4m1l4T0p";
	
	/**
	 * Gera a chave de acesso através de alguns dados do usuário
	 * @param usuario
	 * @return - Chave de acesso
	 * @throws IllegalArgumentException
	 * @throws JWTCreationException
	 * @throws UnsupportedEncodingException
	 */
	public static String gerarToken(Usuario usuario) throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {
		
		return JWT.create().
				withIssuer("IANES").withIssuedAt(new Date())
				.withSubject("Authentication")
				.withClaim("id", usuario.getId())
				.withClaim("nome", usuario.getNome())
				.withClaim("tipo", usuario.getTipo().toString())
			.sign(Algorithm.HMAC512(TOKEN_AUTH_CHAVE_PRIVADA));
		
	}
	
	/**
	 * Decodifica o token e consequentemente extrai o usuário
	 * @param token
	 * @return Usuario
	 */
	public static Usuario getUsuarioDoToken(String token) {
		Usuario usuario = new Usuario();
		
		DecodedJWT decodedJWT = JWT.decode(token);
		usuario.setId(decodedJWT.getClaim("id").asLong());
		usuario.setNome(decodedJWT.getClaim("nome").asString());
		usuario.setTipo(TipoUsuario.valueOf(decodedJWT.getClaim("tipo").asString()));
		
		return usuario;
	}
	
	/**
	 * Valida se a chave de acesso é valida
	 * @param token
	 * @throws JWTVerificationException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static void validarToken(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		JWT.require(Algorithm.HMAC512(TOKEN_AUTH_CHAVE_PRIVADA)).build().verify(token);
	}
	
}
