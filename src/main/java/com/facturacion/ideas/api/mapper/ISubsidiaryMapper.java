package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.List;


import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryMapper {

	Subsidiary mapperToEntity(SubsidiaryNewDTO subsidiaryNewDTO) throws ParseException ;

	SubsidiaryResponseDTO mapperToDTO(Subsidiary subsidiary);

	List<SubsidiaryResponseDTO> mapperToDTO(List<Subsidiary> subsidiaries);
}
