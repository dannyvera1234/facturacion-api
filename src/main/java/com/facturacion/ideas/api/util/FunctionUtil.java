package com.facturacion.ideas.api.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FunctionUtil {


	/**
	 * Geneera una respuesta 
	 * @param status : Codigo {@link HttpStatus} 
	 * @param data : datos de la respuesta, peuder ser null
	 * @param error : Una cadena de texto que indique el eror, puede ser null
	 * @return
	 */
	public static ResponseEntity<Map<String, Object>> getResponseEntity(HttpStatus status, Object data, String error) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("data", data);
		responseData.put("error", error);

		return new ResponseEntity<>(responseData, status);
	}
	
	public static ResponseEntity<Map<String, Object>> getResponseEntity(HttpStatus status,Object data) {

		Map<String, Object> responseData = new HashMap<>();
		// responseData.put("status", status.value());
		responseData.put("data", data);
		return new ResponseEntity<>(responseData, status);
	}

}
