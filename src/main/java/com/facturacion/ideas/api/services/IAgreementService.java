package com.facturacion.ideas.api.services;

import java.util.List;
import com.facturacion.ideas.api.dto.AgreementDTO;

public interface IAgreementService {

	AgreementDTO save(AgreementDTO agreementDTO);
	
	AgreementDTO update(AgreementDTO agreementDTO, String ide);
	
	void deleteById(String codigo);
	
	List<AgreementDTO> listAll();
	
	AgreementDTO findById(String codigo);
}
