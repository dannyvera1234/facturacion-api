package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.TaxValueResponseDTO;
import com.facturacion.ideas.api.entities.TaxValue;

@Component
public class TaxValueMapperImpl implements ITaxValueMapper {

	@Override
	public TaxValueResponseDTO mapperToDTO(TaxValue taxValue) {

		TaxValueResponseDTO taxValueDTO = new TaxValueResponseDTO();
		taxValueDTO.setIde(taxValue.getIde());
		taxValueDTO.setCode(taxValue.getCode());
		taxValueDTO.setDescription(taxValue.getDescription());

		return taxValueDTO;
	}

	@Override
	public List<TaxValueResponseDTO> mapperToDTO(List<TaxValue> taxValues) {

		List<TaxValueResponseDTO> taxValueResponseDTOs = new ArrayList<>();

		if (taxValues.size() > 0)
			taxValueResponseDTOs = taxValues.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());

		return taxValueResponseDTOs;
	}

}
