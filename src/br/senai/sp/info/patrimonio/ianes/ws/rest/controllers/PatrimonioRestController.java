package br.senai.sp.info.patrimonio.ianes.ws.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.info.patrimonio.ianes.exceptions.EntidadeNaoEncontrada;
import br.senai.sp.info.patrimonio.ianes.services.PatrimonioService;

@RestController
@RequestMapping("/rest/patrimonios")
public class PatrimonioRestController {

	@Autowired
	private PatrimonioService patrimonioService;
	
	/**
	 * Busca todos os patrimônios cadastrados
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Object> buscarTodos(){
		
		try {
			return ResponseEntity.ok(patrimonioService.buscarTodos());
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorUm(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(patrimonioService.buscarPorId(id));
		} catch (EntidadeNaoEncontrada e) {
			e.printStackTrace();
			return ResponseEntity.notFound().header("X-Reason", "Entidade não encontrada").build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
