package br.senai.sp.info.patrimonio.ianes.ws.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.info.patrimonio.ianes.services.AmbienteService;

@RestController
@RequestMapping("/rest/ambientes")
/**
 * Pega as requisições feitas pelo cliente em relação ao ambiente e as envia ao service.
 * @author Camila Diniz
 *
 */
public class AmbienteRestController {

	@Autowired
	private AmbienteService ambienteService;

	/**
	 * Busca todos os ambientes
	 * @return - Lista de ambientes
	 */
	@GetMapping
	public ResponseEntity<Object> buscarTodos(){
		
		try {
			return ResponseEntity.ok(ambienteService.buscarTodos());
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}	
}