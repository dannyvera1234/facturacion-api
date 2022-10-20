package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.TaxValueResponseDTO;
import com.facturacion.ideas.api.entities.TaxValue;

public interface ITaxValueMapper {

	
	TaxValueResponseDTO mapperToDTO(TaxValue taxValue);
	
	List<TaxValueResponseDTO> mapperToDTO(List<TaxValue> taxValues);
	
	
}
