package com.facturacion.ideas.api.util;

public class FunctionUtil {

	/**
	 * Funcion que genera el codigo para un nuevo establecimiento
	 * 
	 * @param numSubsidiary : numero de establecimiento a generar
	 * @return
	 */
	public static String getCodSubsidiary(Integer numSubsidiary) {

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

	/**
	 * Funcion encargada de generar el numero siguiente del establecimiento de un
	 * emisor en particular.
	 * Suma uno al numero actual, si numberMax en null, retorna 1
	 * 
	 * @param numberMax
	 * @return
	 */
	public static Integer getNumberNextSubsidiary(Integer numberMax) {

		return numberMax == null ? 1 : (numberMax + 1);
	}

}
