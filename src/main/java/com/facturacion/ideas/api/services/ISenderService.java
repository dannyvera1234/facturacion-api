package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.services.admin.ISenderAdminService;
import org.springframework.web.multipart.MultipartFile;

public interface ISenderService extends ISenderAdminService {

	SenderResponseDTO save(Long idCount, SenderNewDTO senderNewDTO,  MultipartFile file);

	SenderResponseDTO update(SenderNewDTO senderNewDTO, Long idSender);

	SenderResponseDTO findByRuc(String ruc);

	SenderResponseDTO findById(Long id);




}
