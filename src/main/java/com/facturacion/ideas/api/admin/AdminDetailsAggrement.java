package com.facturacion.ideas.api.admin;

import java.util.Calendar;
import java.util.Date;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.enums.TypeAgreementEnum;

public class AdminDetailsAggrement {

	public static DetailsAggrement create(TypeAgreementEnum typeAgreementEnum, int amount) {

		DetailsAggrement detailsAggrement = new DetailsAggrement();

		detailsAggrement.setDateStart(Calendar.getInstance().getTime());
		detailsAggrement.setAmount(amount);
		detailsAggrement.setDateEnd(getDateEnd(typeAgreementEnum, amount));

		detailsAggrement.setStatus(true);

		return detailsAggrement;

	}

	private static Date getDateEnd(TypeAgreementEnum typeAgreementEnum, int amount) {

		Calendar calendar = Calendar.getInstance();

		if (TypeAgreementEnum.MONTH.name().equals(typeAgreementEnum.name())) {

			calendar.add(Calendar.MONTH,  amount);

		} else if (TypeAgreementEnum.YEAR.name().equals(typeAgreementEnum.name())) {

			calendar.add(Calendar.YEAR, amount);
		} else if (TypeAgreementEnum.YEAR_PLUS.name().equals(typeAgreementEnum.name())) {

			calendar.add(Calendar.YEAR,  amount);
		}

		return calendar.getTime();
	}
}
