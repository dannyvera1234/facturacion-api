package com.facturacion.ideas.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
public class ConfigurationUtil {
	
	/*Modulo para el roblema de serizalizacion y deserializacion
	 * de objetos cuanto estan relacionados
	 * 
	 * Link solucion: 
	 * https://stackoverflow.com/questions/52656517/
	 * no-serializer-found-for-class-org-hibernate-proxy-pojo-bytebuddy-bytebuddyinterc/
	 * 71416675#71416675
	 * 
	 * */
	@Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }
	
	/**
	 * Para desearilizar los request y serializar las respuetas, Spring Boot
	 * crea automaticamente una instancia de ObjectMapper con configuraciones 
	 *  por defecto, se puede personalizar: mirar este tutorial
	 * https://www.baeldung.com/spring-boot-customize-jackson-objectmapper
	 */
}
