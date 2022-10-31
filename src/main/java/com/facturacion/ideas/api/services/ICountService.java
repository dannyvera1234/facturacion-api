package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.services.admin.ICountAdminService;

public interface ICountService extends ICountAdminService {

	CountResponseDTO findCountByRuc(String ruc);

	CountResponseDTO findCountsById(Long id);

	CountResponseDTO updateCount(CountNewDTO countNewDTO, Long idCount);
	

	// AgreementDetails
	DetailsAgreementDTO saveDetailsAgreementDTO(Long idCount, Long coddeAgreement);

	LoginDTO saveLoginIn(Long idCount);

	List<LoginDTO> findAllLogin(Long idCount);

	List<DetailsAgreementDTO> listAgreementDetailsAllByRuc(String ruc);

}
