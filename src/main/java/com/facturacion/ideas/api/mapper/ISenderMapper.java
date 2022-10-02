package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;

public interface ISenderMapper {

	Sender mapperToEntity(SenderNewDTO senderNewDTO);

	SenderResponseDTO mapperToDTO(Sender sender);

	List<SenderResponseDTO> mapperToDTO(List<Sender> senders);

}
