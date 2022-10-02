package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.List;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.entities.DetailsAggrement;

public interface IDetailsAgreementMapper {

	DetailsAggrement mapperToEntity(DetailsAgreementDTO detailsAgreementDTO) throws ParseException;

	DetailsAgreementDTO mapperToDTO(DetailsAggrement detailsAggrement);

	List<DetailsAgreementDTO> mapperToDTO(List<DetailsAggrement> detailsAggrements);

}
