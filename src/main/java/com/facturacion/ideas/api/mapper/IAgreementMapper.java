package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;

public interface IAgreementMapper {

	Agreement mapperToEntity(AgreementDTO agreementDTO);

	AgreementDTO mapperToDTO(Agreement agreement);

	List<AgreementDTO> mapperToDTO(List<Agreement> agreements);


}
