package com.facturacion.ideas.api.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IAgreementOperation;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para las entidades {@link Agreement}
 * @author Ronny Chamba
 *
 */
@RestController
@RequestMapping("/facturacion/admin/agreements")
public class AgreementRestController implements IAgreementOperation {

	private static final Logger LOGGER = LogManager.getLogger(AgreementRestController.class);

	@Autowired
	private IAgreementService agreementService;

	@Override
	public ResponseEntity<?> save(Agreement agreement) {

		LOGGER.info("Plan a guardar " + agreement);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(agreement.getCodigo());

			if (aggreementOptional.isEmpty()) {

				Agreement agreementSave = agreementService.save(agreement);

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, agreementSave, null);

			} else {
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"Plan  con codigo " + aggreementOptional.get().getCodigo() + " ya esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al guardar Plan: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> findAll() {

		ResponseEntity<?> responseEntity = null;

		try {

			List<Agreement> listAgreement = agreementService.listAll();

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, listAgreement, null);

		} catch (DataAccessException e) {

			LOGGER.error("Error al listar todos los Planes: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> findById(String codigo) {

		LOGGER.info("Id Plan a buscar: " + codigo);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementSave = aggreementOptional.get();

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, agreementSave, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Plan  " + codigo + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al buscar Plan: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> deleteById(String codigo) {

		LOGGER.info("Id Plan a eliminar: " + codigo);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementDelete = aggreementOptional.get();

				agreementService.deleteById(agreementDelete.getCodigo());

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, agreementDelete, null);

			} else {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Plan  " + codigo + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al eliminar Plan: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> update(Agreement agreement, String codigo) {

		LOGGER.info("Plan a actualizar " + agreement);

		ResponseEntity<?> responseEntity = null;

		try {

			Optional<Agreement> agreementOptional = agreementService.findById(codigo);

			if (!agreementOptional.isEmpty()) {

				Agreement agreementCurrent = agreementOptional.get();
				agreementCurrent.setValue(agreement.getValue());

				agreementCurrent = agreementService.save(agreementCurrent);

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, agreementCurrent, null);

			} else {
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST, null,
						"Plan  con codigo " +codigo + " no esta registrado");
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error al actualizar Plan: ", e);
			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

}
