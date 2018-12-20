package br.senai.sp.info.patrimonio.ianes.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sp.info.patrimonio.ianes.dao.CategoriaPatrimonioDAO;
import br.senai.sp.info.patrimonio.ianes.models.CategoriaPatrimonio;

@Controller
@RequestMapping("/app/adm/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaPatrimonioDAO dao;
	
	@GetMapping("/deletar")
	public String deletarCategoria(@RequestParam(name = "id", required = true) Long id) {
		
		CategoriaPatrimonio categoria = dao.buscar(id);
		dao.deletar(categoria);
		return "redirect:/app/adm/patrimonio/novo";
	}
	
	/*@GetMapping("/editar")
	public String editarCategoria(@RequestParam(name = "id", required = true) Long id) {
		
		CategoriaPatrimonio categoria = dao.buscar(id);
		return "patrimonio/cadastrarPatrimonio";
	}*/

}
