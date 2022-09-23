package com.facturacion.ideas.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.services.ISenderService;

@RestController
@RequestMapping("/facturacion/counts")
public class CountRestController {

	private static final Logger LOGGER = LogManager.getLogger(CountRestController.class);

	@Autowired
	private ISenderService senderService;

	@Autowired
	private IAgreementService agreementService;

	@PostMapping
	public ResponseEntity<?> saveCount(@RequestBody Count count) {

		LOGGER.info("Cuenta a guardar: " + count);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountByRuc(count.getRuc());

			if (countOptional.isEmpty()) {

				Count countSave = senderService.saveCount(count);

				responseEntity = getResponseEntity(HttpStatus.CREATED, countSave, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"El Ruc " + countOptional.get().getRuc() + " ya esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar cuenta: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findCountById(@PathVariable(required = false) Long id) {

		LOGGER.info("Id Cuenta a buscar: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();
				countSave.getSender();
				responseEntity = getResponseEntity(HttpStatus.OK, countSave, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"La cuenta  " + id + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al buscar cuenta: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping
	public ResponseEntity<?> findCountAll() {

		ResponseEntity<?> responseEntity = null;

		try {

			List<Count> counts = senderService.findCountAll();
		

			/*for(Count c : counts) {
				
				c.getSender();
				c.getLogins().size();
				c.getDetailsAggrement().size();
			}*/
			


			responseEntity = getResponseEntity(HttpStatus.OK, counts, null);

		} catch (DataAccessException e) { 

			LOGGER.error("Error al listar cuentas: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@PutMapping
	public ResponseEntity<?> updateCount(@RequestBody Count count) {

		LOGGER.info("Cuenta a actualizar: " + count);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(count.getIde());

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				countSave.setEstado(count.isEstado());
				countSave.setPassword(count.getPassword());

				countSave = senderService.saveCount(countSave);

				responseEntity = getResponseEntity(HttpStatus.OK, countSave, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"La cuenta  " + count.getIde() + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al actualizar cuenta: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCount(@PathVariable(required = false) Long id) {

		LOGGER.info("Id Cuenta a eliminar: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> countOptional = senderService.findCountById(id);

			if (!countOptional.isEmpty()) {

				Count countSave = countOptional.get();

				senderService.deleteCountById(countSave.getIde());

				responseEntity = getResponseEntity(HttpStatus.OK, null, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"La cuenta  " + id + " no esta registrada");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al eliminar cuenta: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@PostMapping("/{id}/login")
	public ResponseEntity<?> saveLogin(@PathVariable Long id) {

		LOGGER.info("Id Cuenta de Login guardar: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> count = senderService.findCountById(id);

			if (!count.isEmpty()) {

				Count countCurrent = count.get();
				countCurrent.addLogin(new Login());

				LOGGER.info("Cuenta de Login actual: " + countCurrent);

				countCurrent = senderService.updateCount(countCurrent);

				responseEntity = getResponseEntity(HttpStatus.OK, countCurrent, null);

			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Cuenta con id " + id + " no esta registrado");

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar registro Login: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping("/{id}/login")
	public ResponseEntity<?> getListLogin(@PathVariable Long id) {

		LOGGER.info("Id Cuenta: " + id);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> count = senderService.findCountById(id);

			if (!count.isEmpty()) {
				List<Login> listLogins = count.get().getLogins();

				responseEntity = getResponseEntity(HttpStatus.OK, listLogins, null);

			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null, "Cuenta Id " + id + " no registrada");

		} catch (DataAccessException e) {

			LOGGER.error("Error al listar Logins: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	/**
	 * Registrar un nuevo plan
	 * 
	 * @param id     : Id de la cuenta
	 * @param codigo : codig del plan seleccionado
	 * @return
	 */
	@PostMapping("/{id}/agreements/{codigo}")
	public ResponseEntity<?> saveDetailsAggrement(@PathVariable Long id,
			@PathVariable(required = false) String codigo) {

		LOGGER.info(String.format("Id cuenta " + id + " Id Plan : " + codigo));

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Count> count = senderService.findCountById(id);

			if (!count.isEmpty()) {

				Optional<Agreement> agreementOptional = agreementService.findById(codigo);

				if (!agreementOptional.isEmpty()) {

					Count countCurrent = count.get();

					Agreement agreement = agreementOptional.get();

					DetailsAggrement detailsAggrement = new DetailsAggrement();
					detailsAggrement.setDateStart(new Date());
					detailsAggrement.setDateEnd(new Date());
					detailsAggrement.setStatus(true);

					detailsAggrement.setGreement(agreement);

					countCurrent.addDetailsAggrement(detailsAggrement);

					countCurrent = senderService.updateCount(countCurrent);

					responseEntity = getResponseEntity(HttpStatus.OK, countCurrent, null);
				} else
					responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,

							"Plan codigo " + codigo + " no registrado");

			} else
				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,

						"Cuenta Id " + id + " no registrada");

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar registro contrato plan: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	private ResponseEntity<?> getResponseEntity(HttpStatus status, Object data, String error) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("data", data);
		responseData.put("error", error);

		return new ResponseEntity<>(responseData, status);
	}

}
