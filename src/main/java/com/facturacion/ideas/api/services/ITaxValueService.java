package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.TaxValueResponseDTO;

public interface ITaxValueService {
	
	List<TaxValueResponseDTO> findAllIVA();
	
	List<TaxValueResponseDTO> findAllICE();
	
	List<TaxValueResponseDTO> findAllIRBPNR();
	
	
	
}
