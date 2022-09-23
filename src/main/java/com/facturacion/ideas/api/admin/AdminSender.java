package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;

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
	 * Actualiza un Sender : si el nuevo valor de un campo es null, no se realizara ningun 
	 * cambio en dicho campo
	 * @param senderCurrent : El Sender actual consultado desde la Bd
	 * @param senderNewData : Los nuevos datos a actualizar
	 */
	public static void update(Sender senderCurrent, Sender senderNewData) {

		// Setear nuevos valores
		senderCurrent.setSocialReason(senderNewData.getSocialReason() == null ? senderCurrent.getSocialReason()
				: senderNewData.getSocialReason());

		senderCurrent.setCommercialName(senderNewData.getCommercialName() == null ? senderCurrent.getCommercialName() :

				senderNewData.getCommercialName());
		senderCurrent.setLogo(senderNewData.getLogo() == null ? senderCurrent.getLogo() : senderNewData.getLogo());
		senderCurrent.setProvince(

				senderNewData.getProvince() == null ? ProvinceEnum.getProvinceEnum(senderCurrent.getProvince())
						: ProvinceEnum.getProvinceEnum(senderNewData.getProvince()));
		senderCurrent.setTypeEnvironment(

				senderNewData.getTypeEnvironment() == null
						? TypeEnvironmentEnum.getTypeEnvironmentEnum(senderCurrent.getTypeEnvironment())
						:

						TypeEnvironmentEnum.getTypeEnvironmentEnum(senderNewData.getTypeEnvironment()));
		senderCurrent.setMatrixAddress(senderNewData.getMatrixAddress() == null ? senderCurrent.getMatrixAddress()
				: senderNewData.getMatrixAddress());

		senderCurrent.setSpecialContributor(

				senderNewData.getSpecialContributor() == null ? senderCurrent.getSpecialContributor()
						: senderNewData.getSpecialContributor());

		senderCurrent.setTypeEmission(

				senderNewData.getTypeEmission() == null
						? TypeEmissionEnum.getTypeEmissionEnum(senderCurrent.getTypeEmission())
						: TypeEmissionEnum.getTypeEmissionEnum(senderNewData.getTypeEmission()));

		senderCurrent.setTypeSender(
				senderNewData.getTypeSender() == null ? senderCurrent.getTypeSender() : senderNewData.getTypeSender());

		senderCurrent.setAccountancy(senderNewData.getAccountancy() == null ? senderCurrent.getAccountancy()
				: senderNewData.getAccountancy());

	}
}
