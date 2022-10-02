package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.enums.TypeAgreementEnum;

@Component
public class AgreementMapperImpl implements IAgreementMapper {

	@Override
	public Agreement mapperToEntity(AgreementDTO agreementDTO) {

		Agreement agreement = new Agreement();

		agreement.setCodigo(agreementDTO.getCodigo());
		agreement.setTypeAgreement(TypeAgreementEnum.getTypeAgreementEnum(agreementDTO.getTypeAgreement()));
		agreement.setValue(agreementDTO.getValue());

		return agreement;
	}

	@Override
	public AgreementDTO mapperToDTO(Agreement agreement) {

		AgreementDTO agreementDTO = new AgreementDTO();

		agreementDTO.setCodigo(agreement.getCodigo());
		agreementDTO.setTypeAgreement(agreement.getTypeAgreement().name());
		agreementDTO.setValue(agreement.getValue());

		return agreementDTO;
	}

	@Override
	public List<AgreementDTO> mapperToDTO(List<Agreement> agreements) {

		List<AgreementDTO> agreementDTOs = new ArrayList<>();

		if (agreements.size() > 0) {

			agreementDTOs = agreements.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return agreementDTOs;
	}

}
