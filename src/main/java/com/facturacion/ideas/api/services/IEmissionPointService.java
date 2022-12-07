package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;

public interface IEmissionPointService {

	EmissionPointResponseDTO save(EmissionPointNewDTO emissionPointNewDTO);
	
	EmissionPointResponseDTO update(EmissionPointNewDTO emissionPointNewDTO, Long id);

	void deleteById(Long id);

	List<EmissionPointResponseDTO> listAll(Long idSubsidiary);

	List<EmissionPointResponseDTO> listAll();
	
	EmissionPointResponseDTO findByCodeAndSubsidiary(String code, Long idSubbsidiary);
	
	EmissionPointResponseDTO findById(Long ide);
}
