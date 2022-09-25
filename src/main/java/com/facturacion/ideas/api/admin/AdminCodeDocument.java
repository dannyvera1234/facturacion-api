package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;

public class AdminCodeDocument {
	
	/**
	 * funcion que crear un nuevo CodeDocument
	 * @param idCount  : Id cuenta a la que pertenece
	 * @param codSubsidiary : Codigo del {@link Subsidiary}
	 * @param numberNext :  : Valor numerico del codigo de {@link Subsidiary},
	 * @param numberEmissionPoint : Valor numerico del {@link EmissionPoint} del {@link Subsidiary} actual,
	 *      						Si el valor es null, significa que es el primer {@link EmissionPoint}
	 * @return
	 */
	public static CodeDocument create(Long idCount, String codSubsidiary, 
			Integer numberNext, Integer numberEmissionPoint) {

		CodeDocument codeDocument = new CodeDocument();
		codeDocument.setCodeCount(idCount);

		codeDocument.setCodeSubsidiary(codSubsidiary);
		codeDocument.setNumSubsidiary(numberNext);
		codeDocument.setNumEmissionPoint(numberEmissionPoint == null ? 1 : numberEmissionPoint);

		return codeDocument;
	}
}
