package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.DeatailsInvoiceProductDTO;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ResponseWebServiceDTO;
import com.facturacion.ideas.api.entities.InvoiceNumber;
import com.facturacion.ideas.api.entities.Product;

public interface IDocumentService {
	
	
	ResponseWebServiceDTO saveInvoice(InvoiceNewDTO invoiceNewDTO);
	
	List<InvoiceResposeDTO> findBySender(Long idSender);
	
	void  deletedById(Long id);
	
	int getCurrentSequentialNumberBySubsidiary(String codDcoument, Long idSubsidiary);
	
	void saveInvoiceNumber(InvoiceNumber invoiceNumber);


	List<Product> searchProductsDetailsByIds(List<Long> idsProducts);
	
}
