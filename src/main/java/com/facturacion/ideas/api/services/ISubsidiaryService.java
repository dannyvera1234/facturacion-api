package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;

public interface ISubsidiaryService {

	SubsidiaryResponseDTO save(SubsidiaryNewDTO subsidiaryNewDTO, Long idSender);
	
	SubsidiaryResponseDTO  update(SubsidiaryNewDTO subsidiaryNewDTO, Long id);

	void deleteById(Long ide);

	List<SubsidiaryResponseDTO> findAll(Long idSender);
	
	SubsidiaryResponseDTO findById(Long ide);
	
	List<SubsidiaryResponseDTO> findByCodeAndSender(String code, Long idSender);

	List<SubsidiaryAndEmissionPointDTO>  listSubsidiaryAndEmissionPointDTOByRuc(String ruc);
}
