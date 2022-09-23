package com.facturacion.ideas.api.admin;

import com.facturacion.ideas.api.entities.CodeDocument;

public class AdminCodeDocument {

	public static CodeDocument create(Long idCount, String codSubsidiary, Integer numberNext) {

		CodeDocument codeDocument = new CodeDocument();
		codeDocument.setCodeCount(idCount);

		codeDocument.setCodeSubsidiary(codSubsidiary);
		codeDocument.setNumSubsidiary(numberNext);
		codeDocument.setNumEmissionPoint(1);

		return codeDocument;
	}
}
