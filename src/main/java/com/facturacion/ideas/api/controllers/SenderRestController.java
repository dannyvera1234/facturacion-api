package com.facturacion.ideas.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturacion/counts")
public class SenderRestController {

	private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

	
	@GetMapping("/senders")
	public String saludar() {
		return "Hola Mundo";
	}
	
	
	
	
}
