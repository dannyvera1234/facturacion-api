package com.facturacion.ideas.api.admin;

import java.util.Calendar;
import java.util.Date;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.enums.TypeAgreementEnum;

public class AdminDetailsAggrement {

	public static DetailsAggrement create(TypeAgreementEnum typeAgreementEnum) {

		DetailsAggrement detailsAggrement = new DetailsAggrement();

		detailsAggrement.setDateStart(Calendar.getInstance().getTime());

		detailsAggrement.setDateEnd(getDateEnd(typeAgreementEnum));
		
		detailsAggrement.setStatus(true);

		return detailsAggrement;

	}

	private static Date getDateEnd(TypeAgreementEnum typeAgreementEnum) {

		Calendar calendar = Calendar.getInstance();

		if (TypeAgreementEnum.MONTH.name().equals(typeAgreementEnum.name())) {

			calendar.add(Calendar.MONTH, typeAgreementEnum.getNumber());

		} else if (TypeAgreementEnum.YEAR.name().equals(typeAgreementEnum.name())) {

			calendar.add(Calendar.YEAR, typeAgreementEnum.getNumber());
		}

		return calendar.getTime();
	}
}
