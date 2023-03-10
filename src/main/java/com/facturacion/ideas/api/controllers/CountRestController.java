package com.facturacion.ideas.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.facturacion.ideas.api.controller.operation.ICountOperation;
import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.ICountService;
import com.facturacion.ideas.api.util.ConstanteUtil;

/**
 * RestController que expone servicios web para las entidades {@link Count} ,
 * {@link Agreement}
 * 
 * @author Ronny Chamba
 *
 */
@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/counts")
public class CountRestController implements ICountOperation {

	private static final Logger LOGGER = LogManager.getLogger(CountRestController.class);

	@Autowired
	private ICountService countService;

	@Override
	public ResponseEntity<CountResponseDTO> findById(Long id) {

		LOGGER.info("Id Cuenta a buscar: " + id);

		try {

			CountResponseDTO countResponseDTO = countService.findCountsById(id);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<CountResponseDTO> findByRuc(String ruc) {

		LOGGER.info("ruc Cuenta a buscar: " + ruc);

		try {

			CountResponseDTO countResponseDTO = countService.findCountByRuc(ruc);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}

	}
	@Override
	public ResponseEntity<CountResponseDTO> update(CountNewDTO countNewDTO, Long id) {

		LOGGER.info("Cuenta a actualizar: " + id);

		try {

			CountResponseDTO countResponseDTO = countService.updateCount(countNewDTO, id);

			return ResponseEntity.ok(countResponseDTO);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}


	@Override
	public ResponseEntity<LoginDTO> saveLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		try {

			LoginDTO loginDTO = countService.saveLoginIn(idCount);

			return ResponseEntity.ok(loginDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}
	@Override
	public ResponseEntity<Map<String, Object>> listAgreementDetailsAllByRuc(String ruc) {


		LOGGER.info("Ruc cuenta a ver planes: " + ruc);

		try {

			List<DetailsAgreementDTO> detailsAgreementDTOS = countService.listAgreementDetailsAllByRuc(ruc);

			Map<String, Object> mapaData= new HashMap<>();
			mapaData.put("data", detailsAgreementDTOS);
			mapaData.put("size", detailsAgreementDTOS.size());

			return  ResponseEntity.ok(mapaData);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<List<LoginDTO>> findAllLogin(Long idCount) {

		LOGGER.info("Id Cuenta de Login guardar: " + idCount);

		try {

			List<LoginDTO> loginDTOs = countService.findAllLogin(idCount);

			return ResponseEntity.ok(loginDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}

	}
}
