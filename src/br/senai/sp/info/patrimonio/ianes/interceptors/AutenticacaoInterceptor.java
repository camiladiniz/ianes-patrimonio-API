package br.senai.sp.info.patrimonio.ianes.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.senai.sp.info.patrimonio.ianes.models.TipoUsuario;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Component
public class AutenticacaoInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private HttpSession session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean necessitaAutenticacao = request.getRequestURI().contains("/app");
		Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
		boolean usuarioEstaAutenticado = usuarioAutenticado != null;
		boolean necessitaSerAdministrador = request.getRequestURI().contains("/adm");
		
		//CASO A PÁGINA PRECISE DE AUTENTICAÇÃO
		if(necessitaAutenticacao) {
			//CASO O USUÁRIO ESTEJA LOGADO
			if(usuarioEstaAutenticado) {
				//CASO O USÁRIO RPECISE SER ADMINISTRADOR
				System.out.println(usuarioAutenticado.getTipo());
				if(necessitaSerAdministrador && usuarioAutenticado.getTipo() != TipoUsuario.ADMINISTRADOR) {
					response.sendError(403);//proibido
					return false;
				}
				
			//CASO O USUÁRIO NÃO ESTEJA AUTENTICADO
			}else {
				response.sendError(401);
				return false;
			}
		}
		
		return true;//pode acessar
	}
}
