package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.dto.TaxValueResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.ITaxValueService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/tax-value")
public class TaxValueController {

	@Autowired
	private ITaxValueService taxValueService;

	@GetMapping("/iva")
	public List<TaxValueResponseDTO> findAllIVA() {

		try {

			List<TaxValueResponseDTO> taxValueResponseDTOs = taxValueService.findAllIVA();

			
			return taxValueResponseDTOs;
		} catch (NotDataAccessException e) {
			
			throw new NotDataAccessException(e.getMessage());
			
		}

	}
	
	@GetMapping("/ice")
	public List<TaxValueResponseDTO> findAllICE() {

		try {

			List<TaxValueResponseDTO> taxValueResponseDTOs = taxValueService.findAllICE();

			return taxValueResponseDTOs;

		} catch (NotDataAccessException e) {
			
			throw new NotDataAccessException(e.getMessage());
			
		}

	}
	
	@GetMapping("/irbpnr")
	public List<TaxValueResponseDTO> findAllIRBPNR() {

		try {

			List<TaxValueResponseDTO> taxValueResponseDTOs = taxValueService.findAllIRBPNR();

			return taxValueResponseDTOs;

		} catch (NotDataAccessException e) {
			
			throw new NotDataAccessException(e.getMessage());
			
		}

	}

}
