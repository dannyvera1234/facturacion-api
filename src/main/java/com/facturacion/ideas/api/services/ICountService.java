package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Sender;

public interface ICountService {

	// Count
	CountResponseDTO saveCount(CountNewDTO countNewDTO);

	CountResponseDTO findCountByRuc(String ruc);

	CountResponseDTO findCountsById(Long id);

	List<CountResponseDTO> findCountAll();

	void deleteCountById(Long id);

	CountResponseDTO updateCount(CountNewDTO countNewDTO, Long idCount);

	// AgreementDetails
	DetailsAgreementDTO saveDetailsAgreementDTO(Long idCount, Long coddeAgreement);
	
	
	LoginDTO saveLoginIn(Long idCount);

	List<LoginDTO> findAllLogin(Long idCount);

	// Sender
	Sender saveSender(Sender sender);

	Optional<Sender> findSenderByRuc(String ruc);

	Optional<Sender> findSenderById(Long id);

	Optional<Boolean> senderIsExiste(String ruc);

	List<Sender> findSenderAll();

}
