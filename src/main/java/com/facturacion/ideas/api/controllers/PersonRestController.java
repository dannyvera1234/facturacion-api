package com.facturacion.ideas.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IPersonService;

@RestController
@RequestMapping("/facturacion")
public class PersonRestController {

	@Autowired
	private IPersonService personService;

	@PostMapping("/senders/{id}/customers")
	public ResponseEntity<CustomerResponseDTO> saveCustomer(@RequestBody CustomerNewDTO customerNewDTO,
			@PathVariable(name = "id") Long idSender) {

		try {

			CustomerResponseDTO customerResponseDTO = personService.save(customerNewDTO, idSender);

			return ResponseEntity.ok(customerResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@PostMapping("/senders/{id}/drivers")
	public ResponseEntity<DriverResponseDTO> saveDriver(@RequestBody DriverNewDTO driverNewDTO,
			@PathVariable(name = "id") Long idSender) {

		try {

			DriverResponseDTO driverResponseDTO = personService.save(driverNewDTO, idSender);

			return ResponseEntity.ok(driverResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	/**
	 * En si, solo se elimina el registro de la rleacion emisor/personas en
	 * DetailsPerson, pero la persona sigue registrada en el sistema, solo que ahora
	 * ya no esta realacion con un emisor
	 * 
	 * @param idSender
	 * @return
	 */
	@DeleteMapping("/details-person/{id}")
	public ResponseEntity<CustomerResponseDTO> deleteById(@PathVariable(name = "id") Long idDetailsPerson) {

		try {

			personService.deleteById(idDetailsPerson);

			return ResponseEntity.noContent().build();
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
