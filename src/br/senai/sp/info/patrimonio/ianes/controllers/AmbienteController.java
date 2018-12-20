package br.senai.sp.info.patrimonio.ianes.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sp.info.patrimonio.ianes.dao.AmbienteDAO;
import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.models.Ambiente;

@Controller
@RequestMapping("/app")
public class AmbienteController {

	@Autowired
	private AmbienteDAO ambienteDAO;
	
	@Autowired
	MovimentacaoDAO movimentacaoDAO;
	
	@Autowired
	ItemPatrimonioDAO itemDAO;
	
	@GetMapping("/ambiente")
	public String abrirListaAmbientes(@RequestParam(name = "id", required = false) Long id, Model model) {
		
		model.addAttribute("ambientes", ambienteDAO.buscarTodos());
		
		if(id == null) {
			model.addAttribute("ambiente", new Ambiente());
		}else {
			model.addAttribute("ambiente", ambienteDAO.buscar(id));
		}
		
		return "ambiente/ambientes";
	}
	
	@PostMapping("/adm/ambiente/salvar")
	public String salvarAmbiente(@Valid Ambiente ambiente, BindingResult brAmbiente, Model model) {

		//Se o nome cadastrado já existir
		if(ambienteDAO.buscarPorNome(ambiente.getNome()) != null) {
			brAmbiente.addError(new FieldError("ambiente", "nome", "Esse ambiente já existe!"));
		}
		
		if(brAmbiente.hasErrors()) {
			model.addAttribute("ambientes", ambienteDAO.buscarTodos());
			return "ambiente/ambientes";
		}
		
		if(ambiente.getId() == null) {
			ambienteDAO.persistir(ambiente);
		}else {
			Ambiente ambienteBd = ambienteDAO.buscar(ambiente.getId());
			BeanUtils.copyProperties(ambiente, ambienteBd);
			ambienteDAO.alterar(ambienteBd);
		}
		
		return "redirect:/app/ambiente";
	}
	
	@GetMapping("/adm/ambiente/excluir")
	public String deletarAmbiente(@RequestParam(name = "id", required = true) Long id) {
		
		Ambiente ambiente = ambienteDAO.buscar(id);
		//movimentacaoDAO.deletarPorAmbiente(id);
		//itemDAO.deletarPorAmbiente(id);
		ambienteDAO.deletar(ambiente);
		
		return "redirect:/app/ambiente";
		
	}
}
