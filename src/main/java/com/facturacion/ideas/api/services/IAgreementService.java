package com.facturacion.ideas.api.services;

import java.util.List;
import com.facturacion.ideas.api.dto.AgreementDTO;

public interface IAgreementService {

	AgreementDTO save(AgreementDTO agreementDTO);
	
	AgreementDTO update(AgreementDTO agreementDTO, Long code);
	
	void deleteById(Long codigo);
	
	List<AgreementDTO> listAll();
	
	AgreementDTO findById(Long  codigo);
}
