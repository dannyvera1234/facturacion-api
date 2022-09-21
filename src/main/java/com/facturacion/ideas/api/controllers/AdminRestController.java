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

import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.services.IAgreementService;

@RestController
@RequestMapping("/facturacion/admin")
public class AdminRestController {

	private static final Logger LOGGER = LogManager.getLogger(AdminRestController.class);

	@Autowired
	private IAgreementService agreementService;

	@PostMapping("/aggreement")
	public ResponseEntity<?> saveAgreement(@RequestBody Agreement agreement) {

		LOGGER.info("Plan a guardar " + agreement);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(agreement.getCodigo());

			if (aggreementOptional.isEmpty()) {

				Agreement agreementSave = agreementService.save(agreement);

				responseEntity = getResponseEntity(HttpStatus.OK, agreementSave, null);

			} else {
				responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"Plan  con codigo " + aggreementOptional.get().getCodigo() + " ya esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar Plan: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping("/aggreement/{codigo}")
	public ResponseEntity<?> findAgreementById(@PathVariable(required = false) String codigo) {

		LOGGER.info("Id Plan a buscar: " + codigo);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementSave = aggreementOptional.get();

				responseEntity = getResponseEntity(HttpStatus.OK, agreementSave, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Plan  " + codigo + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al buscar Plan: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@GetMapping("/aggreement")
	public ResponseEntity<?> findAllAgreement() {

		ResponseEntity<?> responseEntity = null;

		try {

			List<Agreement> listAgreement = agreementService.listAll();

			responseEntity = getResponseEntity(HttpStatus.OK, listAgreement, null);

		} catch (DataAccessException e) {

			LOGGER.error("Error al listar todos los Planes: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@DeleteMapping("/aggreement/{codigo}")
	public ResponseEntity<?> deleteAgreementById(@PathVariable(required = false) String codigo) {

		LOGGER.info("Id Plan a eliminar: " + codigo);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementDelete = aggreementOptional.get();

				agreementService.deleteById(agreementDelete.getCodigo());

				responseEntity = getResponseEntity(HttpStatus.OK, agreementDelete, null);

			} else {

				responseEntity = getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Plan  " + codigo + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al eliminar Plan: ", e);
			responseEntity = getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@PutMapping("/aggreement")
	public ResponseEntity<?> updateAgreement(@RequestBody Agreement agreement) {

		LOGGER.info("Plan a actualizar " + agreement);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> agreementOptional = agreementService.findById(agreement.getCodigo());

			if (!agreementOptional.isEmpty()) {

				Agreement agreementCurrent = agreementOptional.get();
				agreementCurrent.setValue(agreement.getValue());

				agreementCurrent = agreementService.save(agreementCurrent);

				responseEntity = getResponseEntity(HttpStatus.OK, agreementCurrent, null);

			} else {
				responseEntity = getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"Plan  con codigo " + agreement.getCodigo() + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al actualizar Plan: ", e);
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
