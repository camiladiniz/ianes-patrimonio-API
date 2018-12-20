package br.senai.sp.info.patrimonio.ianes.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sp.info.patrimonio.ianes.dao.AmbienteDAO;
import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.dao.PatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.UsuarioDAO;
import br.senai.sp.info.patrimonio.ianes.models.Ambiente;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Controller
@RequestMapping("/app")
public class ItemController {
	
	@Autowired
	ItemPatrimonioDAO itemDAO;
	
	@Autowired
	AmbienteDAO ambienteDAO;
	
	@Autowired
	PatrimonioDAO patrimonioDAO;
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@GetMapping({"/", ""})
	public String abrirLista(Model model, @RequestParam(name = "id", required = false) Long ambienteId, @RequestParam(name = "patId", required = false) Long patrimonioId) {
		System.out.println("id ambiente: " +ambienteId);
		
		if(ambienteId != null) {
			model.addAttribute("itens", itemDAO.buscarItensDoAmbiente(ambienteId));
		}else {		
			model.addAttribute("itens", itemDAO.buscarTodos());
		}
		
		if(patrimonioId != null) {
			model.addAttribute("itens", itemDAO.buscarItensDoPatrimonio(patrimonioId));
		}
		
		model.addAttribute("ambientes", ambienteDAO.buscarTodos());
		model.addAttribute("patrimonios", patrimonioDAO.buscarTodos());
		return "item/lista";
	}

	@GetMapping("/item/novo")
	public String abrirFormItem(Model model) {
		model.addAttribute("item", new ItemPatrimonio());
		model.addAttribute("patrimonios", patrimonioDAO.buscarTodos());
		model.addAttribute("ambientes", ambienteDAO.buscarTodos());
		return "item/form";
	}
	
	@GetMapping("/adm/item/excluir")
	public String excluirItem(@RequestParam(name = "id", required = true) Long id) {
		ItemPatrimonio item = itemDAO.buscar(id);
		itemDAO.deletar(item);
		return "redirect:/app";
	}
	
	@PostMapping("/item/salvar")
	public String salvarItem(@Valid ItemPatrimonio item, BindingResult brItem, Model model, HttpSession session) {
		
		Usuario logado = (Usuario) session.getAttribute("usuarioAutenticado");
		item.setUsuario(logado);
		
		if(brItem.hasErrors()) {
			model.addAttribute("ambientes", ambienteDAO.buscarTodos());
			model.addAttribute("patrimonios", patrimonioDAO.buscarTodos());
			return "item/form";
		}
		
		itemDAO.persistir(item);
		return "redirect:/app";
	}
}
