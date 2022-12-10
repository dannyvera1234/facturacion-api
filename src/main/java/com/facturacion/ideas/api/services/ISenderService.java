package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.services.admin.ISenderAdminService;
import org.springframework.web.multipart.MultipartFile;

public interface ISenderService extends ISenderAdminService {

	SenderResponseDTO save(SenderNewDTO senderNewDTO, MultipartFile logo,
						   MultipartFile certicado);

	SenderResponseDTO update(SenderNewDTO senderNewDTO, MultipartFile logo,
							 MultipartFile certicado);

	SenderResponseDTO findByRuc(String ruc);

	SenderResponseDTO findById(Long id);

	SenderNewDTO findToEdit();

	Optional<String> findNameSenderByRuc(String ruc);

	Long findIdByRuc(String ruc);




}
