package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IEmissionPointOperation;
import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IEmissionPointService;
import com.facturacion.ideas.api.util.ConstanteUtil;

/**
 * RestController que expone servicios web para la entidad {@link EmissionPoint}
 * 
 * @author Ronny Chamba
 *
 */

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class EmissionPointController implements IEmissionPointOperation {

	private static final Logger LOGGER = LogManager.getLogger(EmissionPointController.class);

	@Autowired
	private IEmissionPointService emissionPointService;

	@Override
	public ResponseEntity<EmissionPointResponseDTO> save(EmissionPointNewDTO emissionPointNewDTO) {

		LOGGER.info("Punto emision guardar: " + emissionPointNewDTO);
	
		try {
			EmissionPointResponseDTO emissionPointResponseDTO = emissionPointService.save(emissionPointNewDTO);

			return ResponseEntity.ok(emissionPointResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<EmissionPointResponseDTO>> findAll(Long codigo) {
		LOGGER.info("Id Establecimiento listtar PuntoEmision: " + codigo);
		try {

			List<EmissionPointResponseDTO> emissionPointResponseDTO = emissionPointService.listAll(codigo);

			return ResponseEntity.ok(emissionPointResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<EmissionPointResponseDTO>> findAllPoint() {

		try {

			List<EmissionPointResponseDTO> emissionPointResponseDTO = emissionPointService.listAll();

			return ResponseEntity.ok(emissionPointResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<EmissionPointResponseDTO> findById(Long id) {
		LOGGER.info("Id PuntoEmision: " + id);
		try {

			EmissionPointResponseDTO emissionPointResponseDTO = emissionPointService.findById(id);

			return ResponseEntity.ok(emissionPointResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {

		try {

			emissionPointService.deleteById(id);
			return ResponseEntity.noContent().build();

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<EmissionPointResponseDTO> update(EmissionPointNewDTO emissionPointNewDTO, Long id) {

		LOGGER.info("Punto emision actualizar : "+ id);
		try {

			EmissionPointResponseDTO emissionPointResponseDTO = emissionPointService.update(emissionPointNewDTO, id);

			return ResponseEntity.ok(emissionPointResponseDTO);
			
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<EmissionPointResponseDTO> findByCodeAndSubsidiary(Long codigo, String codePoint) {

		LOGGER.info("Id Establecimiento : " + codigo + " codePoint: " + codePoint);
		try {

			EmissionPointResponseDTO emissionPointResponseDTO = emissionPointService.findByCodeAndSubsidiary(codePoint,
					codigo);

			return ResponseEntity.ok(emissionPointResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

}
