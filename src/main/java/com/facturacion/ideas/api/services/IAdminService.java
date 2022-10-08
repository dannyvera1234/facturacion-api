package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;

public interface IAdminService {
	
	CountResponseDTO saveCount(CountNewDTO countNewDTO);
	
	List<CountResponseDTO> fetchByWithAgreement();
	
	CountResponseDTO updateCountStatus(Long ide);
}
