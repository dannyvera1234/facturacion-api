package com.facturacion.ideas.api.admin;

import java.util.Date;

import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.DetailsAggrement;

public class AdminDetailsAggrement {

	public static DetailsAggrement create(Agreement agreement) {

		DetailsAggrement detailsAggrement = new DetailsAggrement();
		detailsAggrement.setDateStart(new Date());
		detailsAggrement.setDateEnd(new Date());
		detailsAggrement.setStatus(true);

		detailsAggrement.setGreement(agreement);

		return detailsAggrement;

	}
}
