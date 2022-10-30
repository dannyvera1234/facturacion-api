package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.EmailSenderNewDTO;
import com.facturacion.ideas.api.entities.EmailSender;
import com.facturacion.ideas.api.util.FunctionUtil;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;

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
		
		sender.setRetentionAgent(senderNewDTO.getRetentionAgent());

		sender.setRimpe(senderNewDTO.isRimpe());

		sender.setTypeEmission(senderNewDTO.getTypeEmission());
		
		sender.setTypeSender( senderNewDTO.getTypeSender());

		sender.setTypeEnvironment(senderNewDTO.getTypeEnvironment());

		sender.setAccountancy(senderNewDTO.getAccountancy());

		sender.setProvince(senderNewDTO.getProvince());

		if (senderNewDTO.getEmailSenderNewDTOList() !=null && senderNewDTO.getEmailSenderNewDTOList().size()>0){

			List<EmailSender> emailSenders = senderNewDTO.getEmailSenderNewDTOList()
							.stream()
					.map(this::mapperToEntity)
					.collect(Collectors.toList());

			sender.setEmailSenders(emailSenders);
		}


		return sender;
	}

	@Override
	public SenderResponseDTO mapperToDTO(Sender sender) {

		SenderResponseDTO senderResponseDTO = new SenderResponseDTO();
	
		senderResponseDTO.setFullNameSocialReason(sender.getSocialReason() + " " + sender.getCommercialName());
		senderResponseDTO.setRuc(sender.getRuc());
		senderResponseDTO.setTypeSender( sender.getTypeSender().name());
		senderResponseDTO.setMatrixAddress(sender.getMatrixAddress());
		senderResponseDTO.setAccountancy(sender.getAccountancy().name());
		senderResponseDTO.setTypeEnvironment(  TypeEnvironmentEnum.getTypeEnvironmentEnum( sender.getTypeEnvironment()).name());
		senderResponseDTO.setRimpe(sender.isRimpe());

		senderResponseDTO.setProvince(
				sender.getProvince() == null ? null : ProvinceEnum.getProvinceEnum(sender.getProvince()).getName());

		senderResponseDTO.setSpecialContributor(sender.getSpecialContributor());
		senderResponseDTO.setRetentionAgent( sender.getRetentionAgent());

		senderResponseDTO.setRol( sender.getCount().getRol().name());
		return senderResponseDTO;
	}

	@Override
	public List<SenderResponseDTO> mapperToDTO(List<Sender> senders) {

		List<SenderResponseDTO> senderResponseDTOs = new ArrayList<>();

		if (senders.size() > 0) {

			senderResponseDTOs = senders.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return senderResponseDTOs;
	}

	@Override
	public EmailSender mapperToEntity(EmailSenderNewDTO emailSenderNewDTO) {
		EmailSender emailSender = new EmailSender();
		emailSender.setIde(null);
		emailSender.setEmail(emailSenderNewDTO.getEmail());
		emailSender.setPrincipal(emailSenderNewDTO.isPrincipal());
		emailSender.setDateCreate(new Date());
		return emailSender;
	}
}
