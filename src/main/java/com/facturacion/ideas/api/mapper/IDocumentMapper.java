package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.List;

import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.DeatailsInvoiceProduct;
import com.facturacion.ideas.api.entities.DetailsInvoicePayment;
import com.facturacion.ideas.api.entities.Invoice;
import com.facturacion.ideas.api.entities.ValueInvoice;

public interface IDocumentMapper {
	
	// Invoice
	Invoice mapperToEntity(InvoiceNewDTO invoiceNewDTO)  throws ParseException;
	
	InvoiceResposeDTO mapperToDTO(Invoice invoice);
	
	List<InvoiceResposeDTO> mapperToDTO(List<Invoice> invoices);
	
	
	// ValueInvoice
	ValueInvoice  mapperToEntity(ValueInvoiceNewDTO valueInvoiceNewDTO);
	
	ValueInvoiceResponseDTO  mapperToDTO(ValueInvoice valueInvoice);

	ValueInvoiceNewDTO  mapperToNewDTO(ValueInvoice valueInvoice);
	DeatailsInvoiceProduct mapperToEntity( DeatailsInvoiceProductDTO deatailsInvoiceProductDTO);

	List<DetailsInvoicePayment> mapperToEntity(List<PaymenNewtDTO> paymenNewtDTOS);

	ComprobantesResponseDTO mapperComprobanteToDTO(Invoice invoice);

	List<ComprobantesResponseDTO> mapperComprobanteToDTO(List<Invoice> invoice);
	
	
	
	

}
