package com.facturacion.ideas.api.controllers;

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

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.services.ISenderService;

@RestController
@RequestMapping("/facturacion/counts")
public class SenderController {

	private static final Logger LOGGER = LogManager.getLogger(SenderController.class);

	@Autowired
	private ISenderService senderService;

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

				responseEntity = getResponseEntity(HttpStatus. OK, countSave, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"La cuenta  " + count.getIde()+ " no esta registrada");
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

	private ResponseEntity<?> getResponseEntity(HttpStatus status, Object data, String error) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("data", data);
		responseData.put("error", error);

		return new ResponseEntity<>(responseData, status);
	}

}
