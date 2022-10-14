package com.facturacion.ideas.api.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.enums.TypeDocumentEnum;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.util.ConstanteUtil;

public class AdminDocument {

	public static String generateKeyAcces(InvoiceNewDTO invoiceNewDTO, EmissionPoint emissionPoint) {
		
		// * 1) Fecha Actual
		String today = new SimpleDateFormat(ConstanteUtil.DATE_FORMAT_KEY_ACCESS)
				.format(Calendar.getInstance().getTime());
		
		// Tipo Comprobante
		TypeDocumentEnum typeDocument =  TypeDocumentEnum.getTypeDocumentEnum(invoiceNewDTO.getTypeDocument());
		
		if (typeDocument ==null) {
			throw new NotFoundException("Tipo Documento " + invoiceNewDTO.getTypeDocument() + " no es valido");
		}
		
		// * 2) Codigo del tipo documento
		String codeTypeDocument = typeDocument.getCode();
		
		
		// Obtener el establecimiento al cual pertence el punto emision
		Subsidiary subsidiary = emissionPoint.getSubsidiary();
		
		// Obtener el emisor desde el establecimiento
		Sender sender = subsidiary.getSender();
		
		// * 3) Ruc del emisor
		String ruc = sender.getRuc();
		
		// * 4) Tipo Ambiente
		String codeTypeEnviroment = sender.getTypeEnvironment();
		
		// * 5 Serie : conformada por codigo de establecimiento y codigo punto emision
		String codeSubsidiary = subsidiary.getCode();
		String codeEmissionPoint = emissionPoint.getCodePoint();
		
		
		// *
		String keyAccess = today + codeTypeDocument +  ruc +  codeTypeEnviroment +  codeSubsidiary +  codeEmissionPoint;

				
		return null;

	}

}
