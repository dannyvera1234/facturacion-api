package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.List;

import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;

public interface IEmissionPointMapper {

	EmissionPoint mapperToEntity(EmissionPointNewDTO emissionPointNewDTO)  throws ParseException;

	EmissionPointResponseDTO mapperToDTO(EmissionPoint emissionPoint);

	List<EmissionPointResponseDTO> mapperToDTO(List<EmissionPoint> emissionPoints);
}
