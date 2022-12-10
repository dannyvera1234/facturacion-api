package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.EmailSenderNewDTO;
import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.EmailSender;
import com.facturacion.ideas.api.entities.Sender;

public interface ISenderMapper extends  IEmailEmisorMapper {

	Sender mapperToEntity(SenderNewDTO senderNewDTO);

	SenderResponseDTO mapperToDTO(Sender sender);

	SenderNewDTO mapperToDTOEdit(Sender sender);

	List<SenderResponseDTO> mapperToDTO(List<Sender> senders);

	EmailSenderNewDTO mapperToDTO(EmailSender emailSender);

	List<EmailSenderNewDTO> mapperToDTOEmail(List<EmailSender> emailSender);
}
