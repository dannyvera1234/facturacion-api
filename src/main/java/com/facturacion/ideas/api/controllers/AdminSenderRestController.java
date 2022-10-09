package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IAdminService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/admin")
public class AdminSenderRestController {

	private static final Logger LOGGER = LogManager.getLogger(AdminSenderRestController.class);

	@Autowired
	private IAdminService adminService;

	@GetMapping("/counts/{id}/senders")
	public ResponseEntity<SenderResponseDTO> findByCount(@PathVariable("id") Long id) {

		LOGGER.info("Emisor de la cuenta: " + id);
		try {

			SenderResponseDTO senderResponseDTO = adminService.findByCount(id);
			return ResponseEntity.ok(senderResponseDTO);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());

		}
	}

	@GetMapping("/senders/{id}/subsidiarys")
	public ResponseEntity<List<SubsidiaryAndEmissionPointDTO>> fetchBySenderWithEmissionPoint(@PathVariable Long id) {

		try {

			List<SubsidiaryAndEmissionPointDTO> subsidiaryResponseDTOs = adminService
					.fetchBySenderWithEmissionPoint(id);

			return ResponseEntity.ok(subsidiaryResponseDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
