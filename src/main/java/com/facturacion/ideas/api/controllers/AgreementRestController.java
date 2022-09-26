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
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.services.IAgreementService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para las entidades {@link Agreement}
 * 
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

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(agreement.getCodigo());

			if (aggreementOptional.isEmpty()) {

				Agreement agreementSave = agreementService.save(agreement);

				return FunctionUtil.getResponseEntity(HttpStatus.OK, agreementSave);

			} else {

				throw new DuplicatedResourceException("Id: " + aggreementOptional.get().getCodigo()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);
			}

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar Plan: ", e);

			throw new NotDataAccessException("Error guardar el Plan: " + e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> findAll() {

		try {

			List<Agreement> listAgreement = agreementService.listAll();

			return FunctionUtil.getResponseEntity(HttpStatus.OK, listAgreement);

		} catch (DataAccessException e) {

			LOGGER.error("Error listar Planes: ", e);

			throw new NotDataAccessException("Error istar Planes: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> findById(String codigo) {

		LOGGER.info("Id Plan a buscar: " + codigo);

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementSave = aggreementOptional.get();

				return FunctionUtil.getResponseEntity(HttpStatus.OK, agreementSave);

			} else

				throw new NotFoundException("codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar Plan: ", e);

			throw new NotDataAccessException("Error buscar Plan: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> deleteById(String codigo) {

		LOGGER.info("Id Plan a eliminar: " + codigo);

		try {

			Optional<Agreement> aggreementOptional = agreementService.findById(codigo);

			if (!aggreementOptional.isEmpty()) {

				Agreement agreementDelete = aggreementOptional.get();

				agreementService.deleteById(agreementDelete.getCodigo());

				// NO_CONTENT, NO retornara nada en la respuesta así se le pase algo
				return FunctionUtil.getResponseEntity(HttpStatus.NO_CONTENT, String
						.format("Plan %s fue eliminado con exito", agreementDelete.getTypeAgreement().toString()));

			} else

				throw new NotFoundException("codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error eliminar Plan: ", e);
			throw new NotDataAccessException("Error eliminar Plan: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> update(Agreement agreement, String codigo) {

		LOGGER.info("Plan a actualizar " + agreement);
		try {

			Optional<Agreement> agreementOptional = agreementService.findById(codigo);

			if (!agreementOptional.isEmpty()) {

				Agreement agreementCurrent = agreementOptional.get();
				agreementCurrent.setValue(agreement.getValue());

				agreementCurrent = agreementService.save(agreementCurrent);

				return FunctionUtil.getResponseEntity(HttpStatus.OK, agreementCurrent);

			} else
				throw new NotFoundException("codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error actualizar Plan: ", e);
			throw new NotDataAccessException("Error actualizar Plan: " + e.getMessage());

		}
	}

}
