package com.facturacion.ideas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class FacturacionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturacionApiApplication.class, args);
	}

}
