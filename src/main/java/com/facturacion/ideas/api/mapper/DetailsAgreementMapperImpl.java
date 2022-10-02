package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class DetailsAgreementMapperImpl implements IDetailsAgreementMapper {

	@Override
	public DetailsAggrement mapperToEntity(DetailsAgreementDTO detailsAgreementDTO) throws ParseException {

		DetailsAggrement detailsAggrement = new DetailsAggrement();

		detailsAggrement.setStatus(detailsAgreementDTO.isStatus());

		detailsAggrement.setDateStart(FunctionUtil.convertStringToDate(detailsAgreementDTO.getDateStart()));

		detailsAggrement.setDateEnd(FunctionUtil.convertStringToDate(detailsAgreementDTO.getDateEnd()));

		return detailsAggrement;
	}

	@Override
	public DetailsAgreementDTO mapperToDTO(DetailsAggrement detailsAggrement) {

		DetailsAgreementDTO detailsAgreementDTO = new DetailsAgreementDTO();

		detailsAgreementDTO.setIde(detailsAggrement.getIde());

		detailsAgreementDTO.setStatus(detailsAggrement.isStatus());

		detailsAgreementDTO.setDateStart(FunctionUtil.convertDateToString(detailsAggrement.getDateStart()));

		detailsAgreementDTO.setDateEnd(FunctionUtil.convertDateToString(detailsAggrement.getDateEnd()));

		detailsAgreementDTO.setAgreement(detailsAggrement.getGreement().getTypeAgreement().name());

		return detailsAgreementDTO;
	}

	@Override
	public List<DetailsAgreementDTO> mapperToDTO(List<DetailsAggrement> detailsAggrements) {

		List<DetailsAgreementDTO> detailsAgreementDTOs = new ArrayList<>();

		if (detailsAggrements.size() > 0) {

			detailsAgreementDTOs = detailsAggrements.stream().map(item -> mapperToDTO(item))
					.collect(Collectors.toList());
		}

		return detailsAgreementDTOs;
	}

}
