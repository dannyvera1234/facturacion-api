package com.facturacion.ideas.api.services;

import java.util.Optional;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;

public interface ISenderService {

	SenderResponseDTO save(SenderNewDTO senderNewDTO);

	SenderResponseDTO findByRuc(String ruc);

	SenderResponseDTO findById(Long id);

	Optional<Boolean> senderIsExiste(String ruc);
	
	
	
}
