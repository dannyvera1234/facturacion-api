package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
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

	@GetMapping("/senders/{id}/customers")
	public ResponseEntity<List<CustomerResponseDTO>> findAllCustomers(@PathVariable(name = "id") Long idSender) {

		try {

			List<CustomerResponseDTO> customerResponseDTOs = personService.findAllCustomerBySender(idSender);

			return ResponseEntity.ok(customerResponseDTOs);
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

	@GetMapping("/senders/{id}/drivers")
	public ResponseEntity<List<DriverResponseDTO>> findAllDrivers(@PathVariable(name = "id") Long idSender) {

		try {

			List<DriverResponseDTO> customerResponseDTOs = personService.findAllDriverBySender(idSender);

			return ResponseEntity.ok(customerResponseDTOs);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}


	/**
	 * En si, solo se elimina el registro de la relaacion emisor/personas en
	 * DetailsPerson, pero la persona sigue registrada en el sistema, solo que ahora
	 * ya no esta realacion con un emisor
	 * @param idSender : El id del Emisor
	 * @param idCustomers : Id del Cliente
	 * @return
	 */
	@DeleteMapping("/senders/{id}/customers/{id-customer}")
	public ResponseEntity<String> deleteCustomersById(
					@PathVariable(name = "id") Long idSender,
					@PathVariable(name = "id-customer") Long idCustomer){

		try {
			personService.deleteById(idSender, idCustomer);

			return ResponseEntity.noContent().build();
			
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}
	
	/**
	 * En si, solo se elimina el registro de la relaacion emisor/persona en
	 * DetailsPerson, pero la persona sigue registrada en el sistema, solo que ahora
	 * ya no esta realacion con un emisor
	 * @param idSender : id del Emisor
	 * @param idDriver : id del Transportistas
	 * @return
	 */
	@DeleteMapping("/senders/{id}/drivers/{id-driver}")
	public ResponseEntity<String> deleteDriversById(
					@PathVariable(name = "id") Long idSender,
					@PathVariable(name = "id-driver") Long idDriver){

		try {
			personService.deleteById(idSender, idDriver);

			return ResponseEntity.noContent().build();
			
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
