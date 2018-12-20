package br.senai.sp.info.patrimonio.ianes.ws.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.exceptions.ValidacaoException;
import br.senai.sp.info.patrimonio.ianes.models.Movimentacao;
import br.senai.sp.info.patrimonio.ianes.services.ItemService;
import br.senai.sp.info.patrimonio.ianes.utils.MapUtils;

@RestController
@RequestMapping("/rest/itens")
/**
 * Classe responsável por fazer o intermédio entre a requisição do usuário e sua respectiva resposta.
 * @author Camila Diniz
 *
 */
public class ItemRestController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * Busca todos os itens existentes
	 * @return - lista de itens
	 */
	@GetMapping
	public ResponseEntity<Object> buscarTodos()	{
		
		try {
			return ResponseEntity.ok(itemService.buscarTodos());
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * Busca determinado item através de seu id
	 * @param id
	 * @return - Item solicitado
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorUm(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(itemService.buscarPorId(id));
		} catch (EntidadeNaoEncontrada e) {
			e.printStackTrace();
			return ResponseEntity.notFound().header("X-Reason", "Entidade não encontrada").build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	/**
	 * Movimenta determinado item do seu ambiente atual até o de destino especificado
	 * @param id
	 * @param movimentacao
	 * @param br
	 * @return - Movimentacao
	 */
	@PostMapping("/{id}/movimentacoes")
	public ResponseEntity<Object> movimentar(@PathVariable Long id, @RequestBody @Valid Movimentacao movimentacao, BindingResult br){

		try {
			return ResponseEntity.ok(itemService.movimentarItem(id, movimentacao, br));
		} catch (EntidadeNaoEncontrada e) {
			e.printStackTrace();
			return ResponseEntity.notFound().header("X-Reason", "Entidade não encontrada").build();
		} catch (ValidacaoException e) {
			e.printStackTrace();
			return ResponseEntity.unprocessableEntity().body(MapUtils.mapaDe(br));
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	/**
	 * Busca as movimentações do item que possui o id passado na URL
	 * @param id
	 * @return - Movimentações do item solicitado
	 */
	@GetMapping("/{id}/movimentacoes")
	public ResponseEntity<Object> movimentacoesItemDeterminado(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(itemService.buscarMovimentacoesPorId(id));
		} catch (EntidadeNaoEncontrada e) {
			e.printStackTrace();
			return ResponseEntity.notFound().header("X-Reason", "Entidade não encontrada").build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
		
	}
	
	
	
}
