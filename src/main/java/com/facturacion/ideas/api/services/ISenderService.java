package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;

public interface ISenderService {

	SenderResponseDTO save(SenderNewDTO senderNewDTO, Long idCount);

	SenderResponseDTO update(SenderNewDTO senderNewDTO, Long idSender);

	SenderResponseDTO findByRuc(String ruc);

	SenderResponseDTO findById(Long id);

	List<SenderResponseDTO> findAll();



}
