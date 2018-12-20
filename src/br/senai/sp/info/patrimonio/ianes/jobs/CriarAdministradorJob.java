package br.senai.sp.info.patrimonio.ianes.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.senai.sp.info.patrimonio.ianes.dao.UsuarioDAO;
import br.senai.sp.info.patrimonio.ianes.models.TipoUsuario;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Component
public class CriarAdministradorJob implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		//Cadastrando usuário administrador padrão
		Usuario usuario = new Usuario();
		usuario.setEmail("admin@email.com");
		usuario.setSenha("admin");
		usuario.setNome("Administrador");
		usuario.setSobrenome("do Sistema");
		usuario.setTipo(TipoUsuario.ADMINISTRADOR);
		usuario.hashearSenha();
		
		//Inserir no banco de dados
		if(usuarioDAO.buscarPorEmail(usuario.getEmail()) == null) {
			System.out.println("Cadastrando usuário");
			usuarioDAO.persistir(usuario);
		}else {
			System.out.println("Usuário já cadastrado, prosseguindo com a aplicação normalmente");
		}
	}
	
	
}
