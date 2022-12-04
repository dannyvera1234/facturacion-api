package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IPersonService;
import com.facturacion.ideas.api.util.ConstanteUtil;

import javax.validation.Valid;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class PersonRestController {

	private static final Logger LOGGER = LogManager.getLogger(PersonRestController.class);
	@Autowired
	private IPersonService personService;

	@PostMapping("/senders/{id}/customers")
	public ResponseEntity<CustomerResponseDTO> saveCustomer(@RequestBody @Valid CustomerNewDTO customerNewDTO,
			@PathVariable(name = "id") Long idSender) {

		try {

			CustomerResponseDTO customerResponseDTO = personService.save(customerNewDTO, idSender);

			return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@PutMapping("/customers")
	public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody @Valid CustomerNewDTO customerNewDTO) {

		try {
			LOGGER.info("Customer update: "+ customerNewDTO);

			CustomerResponseDTO customerResponseDTO = personService.update(customerNewDTO);

			return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
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

	/**
	 * El id del emisor no lo tomo en cuenta
	 * @param idSender
	 * @param idCustomer
	 * @return
	 */
	@GetMapping("/senders/{id}/customers/{idcustomer}")
	public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable(name = "id") Long idSender,
																	  @PathVariable(name = "idcustomer") Long idCustomer) {

		try {

			LOGGER.info("idCustomer: " + idCustomer);

			CustomerResponseDTO customerResponseDTO  = personService.findById(idCustomer);

			return ResponseEntity.ok(customerResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@PostMapping("/senders/{id}/drivers")
	public ResponseEntity<DriverResponseDTO> saveDriver(@RequestBody @Valid DriverNewDTO driverNewDTO,
			@PathVariable(name = "id") Long idSender) {

		try {

			DriverResponseDTO driverResponseDTO = personService.save(driverNewDTO, idSender);

			return  new ResponseEntity<>(driverResponseDTO, HttpStatus.CREATED);
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
	 * @param idCustomer : Id del Cliente
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
	@GetMapping("/senders/{id}/customers/search")
	public ResponseEntity<List<CustomerResponseDTO>> searchCustomerByCedulaOrRazonSocial (
			@PathVariable(name = "id") Long idSender,
			@RequestParam(name = "filtro" ,required = false, defaultValue = "") String filtro
	){
		try {
			List<CustomerResponseDTO> persons = personService.searchCustomerByCedulaOrRazonSocial(idSender, filtro);
			return ResponseEntity.ok(persons);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}
	@GetMapping("/senders/{id}/drivers/search")
	public ResponseEntity<List<DriverResponseDTO>> searchDriverByCedulaOrRazonSocial (
			@PathVariable(name = "id") Long idSender,
			@RequestParam(name = "filtro" ,required = false, defaultValue = "") String filtro
	){
		try {
			List<DriverResponseDTO> persons = personService.searchDriverByCedulaOrRazonSocial(idSender, filtro);
			return ResponseEntity.ok(persons);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

}
