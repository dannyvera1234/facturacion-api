package com.facturacion.ideas.api.admin;

import java.util.Date;

import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.util.ConstanteUtil;

public class AdminEmissionPoint {

	/**
	 * Crear un nuevo {@link EmissionPoint}}
	 * @param numberNext : Valor entero que corresponde al numero secuencial para el punto emision
	 * @param rucSender : EL ruc de Sender que es dueña del establecimiento, se utilizara para generar
	 * 					 la clave del EmissionPoint
	 * @return
	 */
	public static EmissionPoint create(Integer numberNext, String rucSender) {

		EmissionPoint emissionPoint = new EmissionPoint();
		emissionPoint.setCodePoint(getCodEmissionPoint(numberNext));
		emissionPoint.setDateRegister(new Date());
		emissionPoint.setKeyPoint(rucSender +"_" +emissionPoint.getCodePoint());
		emissionPoint.setStatus(true);

		return emissionPoint;
	}
	
	/**
	 * Funcion que genera el codigo para un nuevo establecimiento
	 * @param numSubsidiary :  numero de establecimiento a generar
	 * @return  :
	 */
	private static String getCodEmissionPoint(Integer numSubsidiary) {

		if (numSubsidiary != null) {

			String codGenerar = null;

			if (numSubsidiary < 10)
				codGenerar = "00" + numSubsidiary;
			else if (numSubsidiary >= 10 && numSubsidiary < 100)
				codGenerar = "0" + numSubsidiary;

			else
				codGenerar = "" + numSubsidiary;

			return codGenerar;
		}
		return ConstanteUtil.COD_DEFAULT_SUBSIDIARY;

	}
}
