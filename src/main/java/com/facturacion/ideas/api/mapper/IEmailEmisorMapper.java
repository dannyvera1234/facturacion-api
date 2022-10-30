package com.facturacion.ideas.api.mapper;

import com.facturacion.ideas.api.dto.EmailSenderNewDTO;
import com.facturacion.ideas.api.entities.EmailSender;

public interface IEmailEmisorMapper {

    EmailSender mapperToEntity(EmailSenderNewDTO emailSenderNewDTO);
}
