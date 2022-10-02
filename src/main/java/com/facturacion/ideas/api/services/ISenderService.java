package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.entities.Sender;

public interface ISenderService {

	CountResponseDTO saveCount(CountNewDTO countNewDTO);

	// Optional<Count> findCountByRuc(String ruc);

	CountResponseDTO findCountByRuc(String ruc);

	Optional<Count> findCountById(Long id);

	CountResponseDTO findCountsById(Long id);

	List<CountResponseDTO> findCountAll();

	void deleteCountById(Long id);

	Count updateCount(Count count);

	LoginDTO saveLoginIn(Long idCount);

	List<LoginDTO> findAllLogin(Long idCount);

	// Sender
	Sender saveSender(Sender sender);

	Optional<Sender> findSenderByRuc(String ruc);

	Optional<Sender> findSenderById(Long id);

	Optional<Boolean> senderIsExiste(String ruc);

	List<Sender> findSenderAll();

}
