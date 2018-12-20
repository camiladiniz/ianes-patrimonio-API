package br.senai.sp.info.patrimonio.ianes.controllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.info.patrimonio.ianes.dao.UsuarioDAO;
import br.senai.sp.info.patrimonio.ianes.models.TipoUsuario;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;
import br.senai.sp.info.patrimonio.ianes.utils.EmailUtils;
import br.senai.sp.info.patrimonio.ianes.utils.ProjetoStorage;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private ProjetoStorage storage;
	
	@GetMapping(value = {"/", ""})
	public String abrirLogin() {
		return "index";
	}
	
	@GetMapping("/app/adm/usuario/editar")
	public String abrirEdicao(Model model, @RequestParam(name = "id", required = true) Long id, HttpServletResponse response) {
		
		model.addAttribute("usuario", usuarioDAO.buscar(id));
		return "usuario/form";
	}
	
	@GetMapping("/app/adm/usuario")
	public String abrirLista(Model model) {
		
		model.addAttribute("usuarios", usuarioDAO.buscarTodos());
		return "usuario/lista";
		
	}
	
	@GetMapping("/app/adm/usuario/novo")
	public String abrirFormNovoUsuario(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		return "usuario/form";
		
	}
	
	@GetMapping("/app/usuario/senha/nova")
	public String abrirFormAlterarSenha(Model model, HttpSession session) {
		Usuario autenticado = (Usuario) session.getAttribute("usuarioAutenticado");
		model.addAttribute("usuario", autenticado);
		return "usuario/alterarSenha";
		
	}
	
	@GetMapping("/app/adm/usuario/deletar")
	public String deletar(@RequestParam(required = true) Long id, HttpServletResponse response) {
		
		Usuario usuario = usuarioDAO.buscar(id);
		usuarioDAO.deletar(usuario);
		return "redirect:/app/adm/usuario";
	}
	
	@PostMapping("/app/adm/usuario/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult brUsuario, @RequestParam(name = "confirmacaoSenha", required = false) String confirmaSenha, @RequestParam(name = "isAdministrador", required = false) Boolean adm, @RequestPart(name = "foto", required = false) MultipartFile foto) {
		
		if(usuario.getId() == null) {
			
			if(usuario.getSenha().length() < 6 || usuario.getSenha().length() > 20) {
				brUsuario.addError(new FieldError("usuario", "senha", "A senha deve conter de 6 a 20 caracteres!"));
			}
			
			if(! confirmaSenha.equals(usuario.getSenha()) ) {
				brUsuario.addError(new FieldError("usuario", "senha", "As senhas devem ser iguais!"));
			}
			
			if(usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
				brUsuario.addError(new FieldError("usuario", "email", "O e-mail selecionado já está em uso"));
			}
			
			//Se houverem erros o formulário reabre
			if(brUsuario.hasErrors()) {
				System.out.println(brUsuario);
				return "usuario/form";
			}
		}else {
			if(brUsuario.hasFieldErrors("nome") || brUsuario.hasFieldErrors("sobrenome")) {
				return "usuario/form";
			}
		}
		
		//Verificando se o checkbox foi marcado
		if(adm != null) {
			usuario.setTipo(TipoUsuario.ADMINISTRADOR);
		}else {
			usuario.setTipo(TipoUsuario.COMUM);
		}
		
		if(usuario.getId() == null) {
			usuario.hashearSenha();
			usuarioDAO.persistir(usuario);
			
			String titulo = "Bem-vindo ao sistema Ianes!";
			String corpo = "Olá, "+usuario.getNome() +"! Seja bem-vindo ao sistema de gerenciamento de Patrimônios Ianes! " +"Acesse o link: localhost:8080/ianes-patrimonio/ para realizar seu primeiro Login!"; 
		
			try {
				EmailUtils.enviarEmail(titulo, corpo, usuario.getEmail());
			}catch (MessagingException e) {
				e.printStackTrace();
			}
		}else {
			Usuario usuarioBanco = usuarioDAO.buscar(usuario.getId());
			usuarioBanco.setNome(usuario.getNome());
			usuarioBanco.setSobrenome(usuario.getSobrenome());
			usuarioBanco.setTipo(usuario.getTipo());
			
			usuarioDAO.alterar(usuarioBanco);
		}
		
		//Armazenando a foto
		try {
			//System.out.println(usuario.getId());
			//System.out.println(foto.getBytes());
			storage.armazenarFotoDePerfil("foto_" +usuario.getId(), foto.getBytes());
			usuario.setCaminhoFoto(storage.getCaminhoProjeto()+"foto_"+usuario.getId());
		}catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/app/adm/usuario";
		
	}
	
	@PostMapping("/usuario/autenticar")
	public String autenticar(@Valid Usuario usuario, BindingResult brUsuario, HttpSession session) {
		
		usuario.hashearSenha();
		Usuario usuarioBuscado = usuarioDAO.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());
		
		if(usuarioBuscado == null) {
			brUsuario.addError(new FieldError("usuario", "email", "E-mail ou senha incorretos!"));
		}
		
		if(brUsuario.hasFieldErrors("email") || brUsuario.hasFieldErrors("senha")) {
			brUsuario.addError(new FieldError("autenticarUsuario", "senha", "E-mail ou senha incorretos!"));
			return "index";
		}
		session.setAttribute("usuarioAutenticado", usuarioBuscado);
		
		return "redirect:/app/";
	}
	
	@PostMapping("/app/usuario/senha/alterar")
	public String alterarSenha(@Valid Usuario usuario, BindingResult brSenha, HttpSession session, @RequestParam(name = "novaSenha", required = true) String novaSenha) {
		
		if(novaSenha.length() < 6 || novaSenha.length() > 20) {
			brSenha.addError(new FieldError("usuario", "senha", "A senha deve conter de 6 a 20 caracteres!"));
		}
		
		Usuario autenticado = (Usuario) session.getAttribute("usuarioAutenticado");
		usuario.hashearSenha();
		if(! usuario.getSenha().equals(autenticado.getSenha())) {
			brSenha.addError(new FieldError("usuario", "senha", "Senha atual inválida!"));
		}
		
		if(brSenha.hasFieldErrors("senha") || brSenha.hasFieldErrors("novaSenha")) {
			System.out.println(brSenha);
			return "usuario/alterarSenha";
		}
		
		Usuario usuarioAlterado = usuarioDAO.buscar(usuario.getId());
		//BeanUtils.copyProperties(usuario, usuarioAlterado);
		System.out.println("usuarioAlterado" +usuarioAlterado);
		usuarioAlterado.setSenha(novaSenha);
		usuarioAlterado.hashearSenha();
		System.out.println(usuario.toString());
		usuarioDAO.alterar(usuarioAlterado);
		
		return "redirect:/app";
	}
	
	@GetMapping("/app/sair")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	
	
}
