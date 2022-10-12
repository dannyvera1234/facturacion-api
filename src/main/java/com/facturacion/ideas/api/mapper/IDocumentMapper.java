package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.List;

import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceResponseDTO;
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
	
	
	
	
	

}
