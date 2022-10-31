package com.facturacion.ideas.api.controllers;

import java.util.List;


import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.facturacion.ideas.api.controller.operation.ISenderOperation;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.ConstanteUtil;
/**
 * RestController que expone servicios web para la entidad {@link Sender}
 * 
 * @author Ronny Chamba
 *
 */

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class SenderRestController implements ISenderOperation {

	private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

	@Autowired
	private ISenderService senderService;

	@Override
	public ResponseEntity<SenderResponseDTO> save(SenderNewDTO senderNewDTO, Long idCount) {

		LOGGER.info(String.format("Emisor guardar %s  ; Id Cuenta: %s ", senderNewDTO, idCount));

		try {

			SenderResponseDTO senderResponseDTO = senderService.save(senderNewDTO, idCount);

			return  new ResponseEntity<>(senderResponseDTO, HttpStatus.CREATED);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<List<SenderResponseDTO>> findAll() {

		try {

			List<SenderResponseDTO> senderResponseDTOs = senderService.findAll();

			return ResponseEntity.ok(senderResponseDTOs);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<SenderResponseDTO> findById(Long id) {

		try {

			SenderResponseDTO senderResponseDTO = senderService.findById(id);

			return ResponseEntity.ok(senderResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<SenderResponseDTO> update(SenderNewDTO senderNewDTO, Long id) {

		LOGGER.info("Id Emisor: " + id);

		try {

			
			SenderResponseDTO senderResponseDTO = senderService.update(senderNewDTO, id);
			
			return ResponseEntity.ok(senderResponseDTO);
			
			
		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<SenderResponseDTO> findByRuc(String ruc) {
		try {

			SenderResponseDTO senderResponseDTO = senderService.findByRuc(ruc);

			return ResponseEntity.ok(senderResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
