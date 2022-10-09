package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IAdminService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/admin")
public class AdminCountRestController {

	private static final Logger LOGGER = LogManager.getLogger(AdminCountRestController.class);

	@Autowired
	private IAdminService adminService;

	@PostMapping("/counts")
	public ResponseEntity<CountResponseDTO> saveCount(@RequestBody CountNewDTO countNewDTO) {

		LOGGER.info("Cuenta a guardar: " + countNewDTO);

		try {
			CountResponseDTO countResponseDTO = adminService.saveCount(countNewDTO);
			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@GetMapping("/counts")
	public ResponseEntity<List<CountResponseDTO>> findAllCountWithAgreement() {

		try {

			List<CountResponseDTO> countResponseDTOs = adminService.fetchByWithAgreement();

			return ResponseEntity.ok(countResponseDTOs);
		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@PutMapping("/counts/{id}/status")
	public ResponseEntity<CountResponseDTO> updateStatusCount(@PathVariable Long id) {

		try {

			CountResponseDTO countResponseDTO = adminService.updateCountStatus(id);

			return ResponseEntity.ok(countResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}


}
