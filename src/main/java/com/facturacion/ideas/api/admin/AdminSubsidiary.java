package com.facturacion.ideas.api.admin;

import java.util.Date;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.util.ConstanteUtil;

public class AdminSubsidiary {

	/**
	 * Crea un nuevo Establecimiento, cuando se crea un nuevo Establecimiento, automaticamente
	 * se creará el primer punto de emisión para este Establecimiento. Este metodo es llamado
	 * solo cuando se crea un nuevo Sender
	 * @param sender
	 * @param idCount
	 * @param numberNext
	 * @return : Un objeto {@link Subsidiary}
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
	 *  Crea un nuevo Establecimiento, cuando se crea un nuevo Establecimiento, automaticamente
	 * se creará el primer punto de emisión para este Establecimiento. <br>
	 * Este metodo es llamado para agregar  un nuevo establecimineto a un Sender que ya esta registrado en la Base de Datos
	 * @param subsidiary
	 * @param sender
	 * @param idCount
	 * @param numberNext
	 */
	public static void createOther(Subsidiary subsidiary, Sender sender, Long idCount, Integer numberNext) {

		String codSubsidiary = getCodSubsidiary(numberNext);

		subsidiary.setCode(codSubsidiary);
		
		// La direccion de cada establecimiento sera pro defecto la del establecmiento principal 
		subsidiary.setAddress(subsidiary.getAddress() ==null? sender.getMatrixAddress() :
				subsidiary.getAddress());
		
		subsidiary.setDateCreate(new Date());
		subsidiary.setPrincipal(false);
		subsidiary.setStatus(true);
		subsidiary.setSocialReason(sender.getSocialReason());
		
		// Agregar al establecmiento el Emisor
		subsidiary.setSender(sender);
		
		
		// Crear el primer Punto de emision	y agregar al establecimiento recien creado
		// Pasamos Null para que valide como el primer punto de emiion de este establecimiento
		subsidiary.addEmissionPoint(AdminEmissionPoint.create(null, sender.getRuc()));
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
