package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IAgreementOperation;
import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IAgreementService;

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
	public ResponseEntity<AgreementDTO> save(AgreementDTO agreementDTO) {

		LOGGER.info("Plan a guardar " + agreementDTO);

		try {

			AgreementDTO agreementSaved = agreementService.save(agreementDTO);

			return ResponseEntity.ok(agreementSaved);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<List<AgreementDTO>> findAll() {

		try {

			List<AgreementDTO> listAgreement = agreementService.listAll();

			return ResponseEntity.ok(listAgreement);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<AgreementDTO> findById(String codigo) {

		LOGGER.info("Id Plan a buscar: " + codigo);

		try {

			AgreementDTO agreementDTO = agreementService.findById(codigo);

			return ResponseEntity.ok(agreementDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<String> deleteById(String codigo) {

		LOGGER.info("Id Plan a eliminar: " + codigo);

		try {

			agreementService.deleteById(codigo);
			return ResponseEntity.noContent().build();

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<AgreementDTO> update(AgreementDTO agreementDTO, String codigo) {

		LOGGER.info("Plan a actualizar " + agreementDTO);
		try {

			AgreementDTO agreementDTOUpdate = agreementService.update(agreementDTO, codigo);

			return ResponseEntity.ok(agreementDTOUpdate);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

}
