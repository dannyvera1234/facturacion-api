package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.ISubsidiaryOperation;
import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.ISubsidiaryService;
import com.facturacion.ideas.api.util.ConstanteUtil;


/**
 * RestController que expone servicios web para la entidad {@link Subsidiary}
 * 
 * @author Ronny Chamba
 *
 */

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class SubsidiaryRestController implements ISubsidiaryOperation {

	private static final Logger LOGGER = LogManager.getLogger(SubsidiaryRestController.class);

	@Autowired
	private ISubsidiaryService subsidiaryService;

	@Override
	public ResponseEntity<SubsidiaryResponseDTO> save(SubsidiaryNewDTO subsidiaryNewDTO, Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {

			SubsidiaryResponseDTO subsidiaryResponseDTO = subsidiaryService.save(subsidiaryNewDTO, idSender);

			return ResponseEntity.ok(subsidiaryResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<SubsidiaryResponseDTO>> findAll(Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {

			List<SubsidiaryResponseDTO> subsidiaryResponseDTOs = subsidiaryService.findAll(idSender);

			return ResponseEntity.ok(subsidiaryResponseDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

	@Override
	public ResponseEntity<SubsidiaryResponseDTO> findById(Long id) {

		LOGGER.info("Id  establecimiento buscar: " + id);

		try {

			SubsidiaryResponseDTO subsidiaryResponseDTO = subsidiaryService.findById(id);

			return ResponseEntity.ok(subsidiaryResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<SubsidiaryResponseDTO> update(SubsidiaryNewDTO subsidiaryNewDTO, Long id) {

		LOGGER.info("Id  Establecimiento actualizar: " + id);

		try {

			SubsidiaryResponseDTO subsidiaryResponseDTO = subsidiaryService.update(subsidiaryNewDTO, id);

			return ResponseEntity.ok(subsidiaryResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {

		LOGGER.info("Id  establecimiento: " + id);

		try {

			subsidiaryService.deleteById(id);

			return ResponseEntity.noContent().build();
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<List<SubsidiaryResponseDTO>> findByCodeAndSender(Long idSender, String code) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {

			List<SubsidiaryResponseDTO> subsidiaryResponseDTOs = subsidiaryService.findByCodeAndSender(code, idSender);

			return ResponseEntity.ok(subsidiaryResponseDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}

}
