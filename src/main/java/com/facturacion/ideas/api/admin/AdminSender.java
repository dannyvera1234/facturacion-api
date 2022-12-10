package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.*;

public class AdminSender {

    /**
     * Metodo setea el ruc del Sender y asinga la relacion Count
     *
     * @param sender
     * @param countCurrent : Count actual
     */
    public static void create(Sender sender, Count countCurrent) {

        // Por seguridad seteo el ruc de la cuenta
        sender.setRuc(countCurrent.getRuc());
        sender.setCount(countCurrent);

    }

    /**
     * Actualiza un Sender : si el nuevo valor de un campo es null, no se realizara
     * ningun cambio en dicho campo
     *
     * @param senderCurrent : El Sender actual consultado desde la Bd
     * @param senderNewDTO  : Los nuevos datos a actualizar
     */
    public static void update(Sender senderCurrent, SenderNewDTO senderNewDTO) {

        // Setear nuevos valores
        senderCurrent.setSocialReason(senderNewDTO.getSocialReason() == null ? senderCurrent.getSocialReason()
                : senderNewDTO.getSocialReason());

        senderCurrent.setCommercialName(senderNewDTO.getCommercialName() == null ? senderCurrent.getCommercialName() :

                senderNewDTO.getCommercialName());
        //senderCurrent.setLogo(senderNewDTO.getLogo() == null ? senderCurrent.getLogo() : senderNewDTO.getLogo());
        senderCurrent.setProvince(

                senderNewDTO.getProvince() == null ? ProvinceEnum.getProvinceEnum(senderCurrent.getProvince())
                        : ProvinceEnum.getProvinceEnum(senderNewDTO.getProvince()));

        senderCurrent.setTypeEnvironment(

                senderNewDTO.getTypeEnvironment() == null
                        ? TypeEnvironmentEnum.getTypeEnvironmentEnum(senderCurrent.getTypeEnvironment())
                        : TypeEnvironmentEnum.getTypeEnvironmentEnum(senderNewDTO.getTypeEnvironment()));

        senderCurrent.setMatrixAddress(senderNewDTO.getMatrixAddress() == null ? senderCurrent.getMatrixAddress()
                : senderNewDTO.getMatrixAddress());

        senderCurrent.setSpecialContributor(

                senderNewDTO.getSpecialContributor() == null ? senderCurrent.getSpecialContributor()
                        : senderNewDTO.getSpecialContributor());

        senderCurrent.setTypeEmission(

                senderNewDTO.getTypeEmission() == null
                        ? TypeEmissionEnum.getTypeEmissionEnum(senderCurrent.getTypeEmission())
                        : TypeEmissionEnum.getTypeEmissionEnum(senderNewDTO.getTypeEmission()));

        senderCurrent.setTypeSender(
                senderNewDTO.getTypeSender() == null ? senderCurrent.getTypeSender() : TypeSenderEnum.findByCode(senderNewDTO.getTypeSender()));

        senderCurrent.setAccountancy(
                senderNewDTO.getAccountancy() == null ? senderCurrent.getAccountancy() :
                        (senderNewDTO.getAccountancy().equalsIgnoreCase("SI") ? QuestionEnum.SI : QuestionEnum.NO));

    }
}
