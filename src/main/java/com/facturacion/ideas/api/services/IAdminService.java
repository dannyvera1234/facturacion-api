package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;

public interface IAdminService {
	
	CountResponseDTO saveCount(CountNewDTO countNewDTO);
	
	List<CountResponseDTO> fetchByWithAgreement();
	
	CountResponseDTO updateCountStatus(Long ide);
	
	SenderResponseDTO   findByCount(Long idCount);
	
	List<SubsidiaryAndEmissionPointDTO> fetchBySenderWithEmissionPoint(Long idSender);
		
	
	
}
