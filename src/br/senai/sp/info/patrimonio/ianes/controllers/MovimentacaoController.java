package br.senai.sp.info.patrimonio.ianes.controllers;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sp.info.patrimonio.ianes.dao.AmbienteDAO;
import br.senai.sp.info.patrimonio.ianes.dao.ItemPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.dao.MovimentacaoDAO;
import br.senai.sp.info.patrimonio.ianes.models.ItemPatrimonio;
import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;
import br.senai.sp.info.patrimonio.ianes.models.Usuario;

@Controller
@RequestMapping("/app")
public class MovimentacaoController {

	@Autowired
	MovimentacaoDAO movimentacaoDAO;
	
	@Autowired
	AmbienteDAO ambienteDAO;
	
	@Autowired
	ItemPatrimonioDAO itemDAO;
	
	@GetMapping("/movimentacao")
	public String abrirFormMovimentacao(@RequestParam(name = "id", required = false) Long id, Model model) {
		
		model.addAttribute("ambientes", ambienteDAO.buscarTodos());
		model.addAttribute("itens", itemDAO.buscarTodos());
		model.addAttribute("movimentacao", new Movimentacao());
		
		return "movimentacao/movimentacao";
	}
	
	/**
	 * Abre a página de histórico de movimentações
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/movimentacao/historico")
	public String abrirHistorico(@RequestParam(name = "id", required = false) Long id, Model model) {
		
		if(id != null) {
			model.addAttribute("movimentacoes", movimentacaoDAO.buscarMovimentacoesDeDeterminadoItem(id));
		}else {
			model.addAttribute("movimentacoes", movimentacaoDAO.buscarTodos());
		}
		model.addAttribute("itens", itemDAO.buscarTodos());
		return "movimentacao/historico";
	}
	
	@PostMapping("/movimentacao/salvar")
	public String movimentarItem(@Valid Movimentacao movimentacao, BindingResult brMovimentacao, Model model, HttpSession session) {
		
		Usuario autenticado = (Usuario) session.getAttribute("usuarioAutenticado");
		
		ItemPatrimonio item = itemDAO.buscar(movimentacao.getItem().getId());
		
		//Se o ambiente de destino for o mesmo de origem
		if(item.getAmbiente().getId() == movimentacao.getAmbienteDestino().getId()) {
			brMovimentacao.addError(new FieldError("movimentacao", "ambienteDestino", "O item já se encontra neste ambiente!"));
		}
		
		if(brMovimentacao.hasFieldErrors("ambienteDestino")) {
			model.addAttribute("ambientes", ambienteDAO.buscarTodos());
			model.addAttribute("itens", itemDAO.buscarTodos());
			return "movimentacao/movimentacao";
		}
	
		movimentacao.setAmbienteOrigem(item.getAmbiente());
		movimentacao.setDataMovimentacao(new Date());
		movimentacao.setUsuario(autenticado);
		
		//Atualizando o ambiente do item
		item.setAmbiente(movimentacao.getAmbienteDestino());
		item.setDataUltimaMovimentacao(movimentacao.getDataMovimentacao());
		itemDAO.alterar(item);
		
		movimentacaoDAO.persistir(movimentacao);
		
		return "redirect:/app/";
	}
}
