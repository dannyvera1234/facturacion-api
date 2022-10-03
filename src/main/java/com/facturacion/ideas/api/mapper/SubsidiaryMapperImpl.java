package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class SubsidiaryMapperImpl implements ISubsidiaryMapper {

	@Override
	public Subsidiary mapperToEntity(SubsidiaryNewDTO subsidiaryNewDTO) throws ParseException {

		Subsidiary subsidiary = new Subsidiary();

		subsidiary.setCode(subsidiaryNewDTO.getCode());
		subsidiary.setAddress(subsidiaryNewDTO.getAddress());
		subsidiary.setPrincipal(subsidiaryNewDTO.isPrincipal());
		subsidiary.setSocialReason(subsidiaryNewDTO.getSocialReason());
		subsidiary.setStatus(subsidiaryNewDTO.isStatus());
		
		
		subsidiary.setDateCreate(FunctionUtil.convertStringToDate(subsidiaryNewDTO.getDateCreate()));
		return subsidiary;
	}

	@Override
	public SubsidiaryResponseDTO mapperToDTO(Subsidiary subsidiary) {

		SubsidiaryResponseDTO subsidiaryResponseDTO = new SubsidiaryResponseDTO();

		subsidiaryResponseDTO.setCode(subsidiary.getCode());
		subsidiaryResponseDTO.setSocialReason(subsidiary.getSocialReason());
		subsidiaryResponseDTO.setAddress(subsidiary.getAddress());
		subsidiaryResponseDTO.setStatus(subsidiary.isStatus());
		subsidiaryResponseDTO.setDateCreate(FunctionUtil.convertDateToString(subsidiary.getDateCreate()));

		return subsidiaryResponseDTO;
	}

	@Override
	public List<SubsidiaryResponseDTO> mapperToDTO(List<Subsidiary> subsidiaries) {

		List<SubsidiaryResponseDTO> subsidiaryResponseDTOs = new ArrayList<>();

		if (subsidiaries.size() > 0) {

			subsidiaryResponseDTOs = subsidiaries.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return subsidiaryResponseDTOs;
	}

}
