package br.senai.sp.info.patrimonio.ianes.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class MapUtils {

	/**
	 * Converte um {@link BindingResult} em um {@link Map}
	 * @param bindingResult
	 * @return
	 */
	public static Map<String, String> mapaDe(BindingResult bindingResult){
		Map<String, String> errorsMap = new HashMap<>();
		
		for(FieldError erro: bindingResult.getFieldErrors()) {
			errorsMap.put(erro.getField(), erro.getDefaultMessage());
		}
		
		return errorsMap;
	}
	
}
