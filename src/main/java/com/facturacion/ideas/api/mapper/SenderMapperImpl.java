package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.ProvinceEnum;

@Component
public class SenderMapperImpl implements ISenderMapper {

	@Override
	public Sender mapperToEntity(SenderNewDTO senderNewDTO) {

		Sender sender = new Sender();

		sender.setRuc(senderNewDTO.getRuc());

		sender.setSocialReason(senderNewDTO.getSocialReason());

		sender.setCommercialName(senderNewDTO.getCommercialName());

		sender.setLogo(senderNewDTO.getLogo());

		sender.setMatrixAddress(senderNewDTO.getMatrixAddress());

		sender.setSpecialContributor(senderNewDTO.getSpecialContributor());

		sender.setRimpe(senderNewDTO.isRimpe());

		sender.setTypeEmission(senderNewDTO.getTypeEmission());

		sender.setTypeEnvironment(senderNewDTO.getTypeEnvironment());

		sender.setAccountancy(senderNewDTO.getAccountancy());

		sender.setProvince(senderNewDTO.getProvince());

		return sender;
	}

	@Override
	public SenderResponseDTO mapperToDTO(Sender sender) {

		SenderResponseDTO senderResponseDTO = new SenderResponseDTO();
		senderResponseDTO.setFullNameSocialReason(sender.getSocialReason() + " " + sender.getCommercialName());
		senderResponseDTO.setRuc(sender.getRuc());
		senderResponseDTO.setMatrixAddress(sender.getMatrixAddress());
		senderResponseDTO.setRimpe(sender.isRimpe());

		senderResponseDTO.setProvince(
				sender.getProvince() == null ? null : ProvinceEnum.getProvinceEnum(sender.getProvince()).getName());

		senderResponseDTO.setSpecialContributor(sender.getSpecialContributor());

		return senderResponseDTO;
	}

	@Override
	public List<SenderResponseDTO> mapperToDTO(List<Sender> senders) {

		List<SenderResponseDTO> senderResponseDTOs = new ArrayList<>();

		if (senderResponseDTOs.size() > 0) {

			senderResponseDTOs = senders.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return senderResponseDTOs;
	}

}
