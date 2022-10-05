package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.EmissionPointNewDTO;
import com.facturacion.ideas.api.dto.EmissionPointResponseDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class EmissionPointMapperImpl implements IEmissionPointMapper {

	@Override
	public EmissionPoint mapperToEntity(EmissionPointNewDTO emissionPointNewDTO) throws ParseException {

		EmissionPoint emissionPoint = new EmissionPoint();
		emissionPoint.setCodePoint(emissionPointNewDTO.getCodePoint());
		emissionPoint.setDateRegister(FunctionUtil.convertStringToDate(emissionPointNewDTO.getDateRegister()));
		emissionPoint.setKeyPoint(emissionPointNewDTO.getKeyPoint());
		emissionPoint.setStatus(emissionPointNewDTO.isStatus());
		return emissionPoint;
	}

	@Override
	public EmissionPointResponseDTO mapperToDTO(EmissionPoint emissionPoint) {

		EmissionPointResponseDTO emissionPointResponseDTO = new EmissionPointResponseDTO();
		emissionPointResponseDTO.setIde(emissionPoint.getIde());
		emissionPointResponseDTO.setCodePoint(emissionPoint.getCodePoint());
		emissionPointResponseDTO.setDateRegister(FunctionUtil.convertDateToString(emissionPoint.getDateRegister()));
		emissionPointResponseDTO.setKeyPoint(emissionPoint.getKeyPoint());
		emissionPointResponseDTO.setStatus(emissionPoint.isStatus());

		Employee employee = emissionPoint.getEmployee();

		emissionPointResponseDTO.setFullNameEmployee(employee == null ? "No Asignado" : employee.getName());
		return emissionPointResponseDTO;
	}

	@Override
	public List<EmissionPointResponseDTO> mapperToDTO(List<EmissionPoint> emissionPoints) {

		List<EmissionPointResponseDTO> emissionPointResponseDTOs = new ArrayList<>();

		if (emissionPoints.size() > 0) {
			emissionPointResponseDTOs = emissionPoints.stream().map(item -> mapperToDTO(item))
					.collect(Collectors.toList());
		}

		return emissionPointResponseDTOs;
	}

}
