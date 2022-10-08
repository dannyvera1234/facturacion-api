package com.facturacion.ideas.api.services;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.entities.Person;
import com.facturacion.ideas.api.entities.Sender;

public interface IPersonService {

	
	CustomerResponseDTO save(CustomerNewDTO customerNewDTO, Long idSender);
	
	DriverResponseDTO save(DriverNewDTO driverNewDTO, Long idSender);
	
	boolean  existsByPersonAndSender(Long idPerson, Long IdSender);
	
	
	void deleteById(Long idDetailsPerson);
	
}
