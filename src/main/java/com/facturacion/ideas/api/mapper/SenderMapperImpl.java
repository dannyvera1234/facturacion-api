package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.EmailSenderNewDTO;
import com.facturacion.ideas.api.entities.EmailSender;
import com.facturacion.ideas.api.enums.*;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.entities.Sender;

@Component
public class SenderMapperImpl implements ISenderMapper {

    @Override
    public Sender mapperToEntity(SenderNewDTO senderNewDTO) {

        Sender sender = new Sender();

        sender.setRuc(senderNewDTO.getRuc());

        sender.setSocialReason(senderNewDTO.getSocialReason());

        sender.setCommercialName(senderNewDTO.getCommercialName());

         //sender.setLogo(senderNewDTO.getLogo());

        sender.setMatrixAddress(senderNewDTO.getMatrixAddress());

        sender.setSpecialContributor(senderNewDTO.getSpecialContributor());

        sender.setRetentionAgent(senderNewDTO.getRetentionAgent());

        sender.setRimpe(senderNewDTO.isRimpe());

        sender.setTypeEmission(TypeEmissionEnum.getTypeEmissionEnum(senderNewDTO.getTypeEmission()));

        sender.setPasswordCerticate(senderNewDTO.getPasswordCerticate());

        //sender.setNameCerticate(senderNewDTO.getCerticate());

        sender.setTypeSender(TypeSenderEnum.findByCode(senderNewDTO.getTypeSender()));

        sender.setTypeEnvironment(TypeEnvironmentEnum.getTypeEnvironmentEnum(senderNewDTO.getTypeEnvironment()));

        sender.setAccountancy(senderNewDTO.getAccountancy().equalsIgnoreCase("SI")
        ? QuestionEnum.SI : QuestionEnum.NO);

        sender.setProvince(ProvinceEnum.getProvinceEnum(senderNewDTO.getProvince()));

        if (senderNewDTO.getEmail() !=null){
            sender.addEmailSenders( new EmailSender(senderNewDTO.getEmail(), new Date(), true));
        }

        return sender;
    }

    @Override
    public SenderResponseDTO mapperToDTO(Sender sender) {

        SenderResponseDTO senderResponseDTO = new SenderResponseDTO();

        senderResponseDTO.setFullNameSocialReason(sender.getSocialReason() +
                " " + (sender.getCommercialName() == null ? "" : sender.getCommercialName()));
        senderResponseDTO.setRuc(sender.getRuc());
        senderResponseDTO.setTypeSender(sender.getTypeSender().name());
        senderResponseDTO.setMatrixAddress(sender.getMatrixAddress());
        senderResponseDTO.setAccountancy(sender.getAccountancy().name());
        senderResponseDTO.setTypeEnvironment(TypeEnvironmentEnum.getTypeEnvironmentEnum(sender.getTypeEnvironment()).name());
        senderResponseDTO.setRimpe(sender.isRimpe());

        senderResponseDTO.setProvince(
                sender.getProvince() == null ? null : ProvinceEnum.getProvinceEnum(sender.getProvince()).getName());

        senderResponseDTO.setSpecialContributor(sender.getSpecialContributor());
        senderResponseDTO.setRetentionAgent(sender.getRetentionAgent());

        //senderResponseDTO.setRol( sender.getCount().getRol().name());
        return senderResponseDTO;
    }

    @Override
    public SenderNewDTO mapperToDTOEdit(Sender sender) {

        SenderNewDTO senderEditDTO = new SenderNewDTO();

        senderEditDTO.setIde(sender.getIde());
        senderEditDTO.setRuc(sender.getRuc());
        senderEditDTO.setSocialReason(sender.getSocialReason());
        senderEditDTO.setMatrixAddress(sender.getMatrixAddress());
        senderEditDTO.setCommercialName(senderEditDTO.getCommercialName());
        senderEditDTO.setTypeSender(sender.getTypeSender().name());
        senderEditDTO.setAccountancy(sender.getAccountancy().name());
        senderEditDTO.setTypeEnvironment(sender.getTypeEnvironment());
        senderEditDTO.setRimpe(sender.isRimpe());
        senderEditDTO.setTypeEmission(sender.getTypeEmission());
        senderEditDTO.setProvince(
                sender.getProvince() == null ? null : ProvinceEnum.getProvinceEnum(sender.getProvince()).getCode());
        senderEditDTO.setSpecialContributor(sender.getSpecialContributor());

        // Subsifiary y punto emision lo asigno en el service de donde se llama ese mapper
        senderEditDTO.setPasswordCerticate(sender.getPasswordCerticate());
       // senderEditDTO.setLogo(sender.getLogo());

        List<EmailSender> emailSenders = sender.getEmailSenders();
        if (emailSenders.size()>0){
            senderEditDTO.setEmail(emailSenders.get(0).getEmail());
        }
		return senderEditDTO;
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
    public EmailSenderNewDTO mapperToDTO(EmailSender emailSender) {

        EmailSenderNewDTO emailSDTO = new EmailSenderNewDTO();
        emailSDTO.setIde(emailSender.getIde());
        emailSDTO.setEmail(emailSender.getEmail());
        emailSDTO.setPrincipal(emailSender.isPrincipal());
        return emailSDTO;
    }

    @Override
    public List<EmailSenderNewDTO> mapperToDTOEmail(List<EmailSender> emailsSender) {

        if (!emailsSender.isEmpty()) {
            return  emailsSender.stream().map(this::mapperToDTO)
					.collect(Collectors.toList());
        }
        return  new ArrayList<>(0);
    }

    @Override
    public EmailSender mapperToEntity(EmailSenderNewDTO emailSenderNewDTO) {
        EmailSender emailSender = new EmailSender();
        emailSender.setIde(emailSenderNewDTO.getIde());
        emailSender.setEmail(emailSenderNewDTO.getEmail());
        emailSender.setPrincipal(emailSenderNewDTO.isPrincipal());
        emailSender.setDateCreate(new Date());
        return emailSender;
    }
}
