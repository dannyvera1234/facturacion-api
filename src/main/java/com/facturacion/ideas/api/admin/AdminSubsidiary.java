package com.facturacion.ideas.api.admin;

import java.util.Date;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.util.ConstanteUtil;

public class AdminSubsidiary {

	/**
	 * Crea un nuevo Establecimiento, cuando se crea un nuevo Establecimiento, automaticamente
	 * se creará el primer punto de emisión para este Establecimiento
	 * @param sender
	 * @param idCount
	 * @param numberNext
	 * @return
	 */
	public static Subsidiary create(Sender sender, Long idCount, Integer numberNext) {

		Subsidiary subsidiary = new Subsidiary();

		String codSubsidiary = getCodSubsidiary(numberNext);

		subsidiary.setCode(codSubsidiary);
		subsidiary.setAddress(sender.getMatrixAddress());
		subsidiary.setDateCreate(new Date());
		subsidiary.setPrincipal(numberNext == 1);
		subsidiary.setStatus(true);
		subsidiary.setSocialReason(sender.getSocialReason());
		
		
		// Crear el primer Punto de emision	y agregar al establecimiento recien creado
		subsidiary.addEmissionPoint(AdminEmissionPoint.create(numberNext, sender.getRuc()));
		return subsidiary;
	}

	/**
	 * Funcion que genera el codigo para un nuevo establecimiento
	 * 
	 * @param numSubsidiary : numero de establecimiento a generar
	 * @return
	 */
	private static String getCodSubsidiary(Integer numSubsidiary) {

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
	 * emisor en particular. Suma uno al numero actual, si numberMax en null,
	 * retorna 1
	 * 
	 * @param numberMax
	 * @return
	 */
	public static Integer getNumberNextSubsidiary(Integer numberMax) {

		return numberMax == null ? 1 : (numberMax + 1);
	}

}
