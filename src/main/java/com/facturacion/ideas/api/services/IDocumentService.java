package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.InvoiceNumber;
import com.facturacion.ideas.api.entities.Product;

public interface IDocumentService {
	
	
	ResponseWebServiceDTO saveInvoice(InvoiceNewDTO invoiceNewDTO);

	ValueInvoiceNewDTO calcularValoresDocumento( List< DeatailsInvoiceProductDTO> detailsDocument);
	
	List<InvoiceResposeDTO> findBySender(Long idSender);
	
	void  deletedById(Long id);
	
	int getCurrentSequentialNumberByEmissionPoint(String codDcoument, Long idSubsidiary);
	
	void saveInvoiceNumber(InvoiceNumber invoiceNumber);


	List<Product> searchProductsDetailsByIds(List<Long> idsProducts);
	
}
