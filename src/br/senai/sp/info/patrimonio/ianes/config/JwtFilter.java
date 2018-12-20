package br.senai.sp.info.patrimonio.ianes.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import br.senai.sp.info.patrimonio.ianes.models.Usuario;
import br.senai.sp.info.patrimonio.ianes.utils.JwtUtils;

/**
 * Filtro de liberação de acesso do token JWT
 * @author Camila Diniz
 *
 */
@Component
public class JwtFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Convertendo e extraindo os header, pq a aplicação é web (precisa de Http)
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//Extraindo o Header
		String authorization = req.getHeader("Authorization");
		
		//Como nem sempre o cliente envia o header, temos que fazer uma verificação
		if(authorization != null) {
			
			//Verificando se o Authorization é do tipo Bearer <token>
			if(authorization.matches("(Bearer )(\\w|\\.|\\-)+")) {
				//Bearer token -> extrair somente o token do header Authorization
				String token = authorization.split(" ")[1];
				
				try {
					JwtUtils.validarToken(token);
					Usuario usuarioDoToken = JwtUtils.getUsuarioDoToken(token);
					
					//Usuario que está no token é o usuario que está logado
					//pega o contexto que está armazenando
					//Parou de dar erro pq o model Usuario implementou a interface Authentication, ou seja, a classe 'assinou' o contrato
					SecurityContextHolder.getContext().setAuthentication(usuarioDoToken);
				}catch (Exception e) {
					System.out.println("Token inválido!");
					e.printStackTrace();
				}
			}else {
				System.out.println("Authorization não enviada!");
			}
		}
		
		//Fazendo com que as configuracoes definidas cheguem ao controller
		chain.doFilter(request, response);
	}

	
	
}
