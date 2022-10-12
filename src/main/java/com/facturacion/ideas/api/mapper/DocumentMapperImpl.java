package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceNewDTO;
import com.facturacion.ideas.api.dto.ValueInvoiceResponseDTO;
import com.facturacion.ideas.api.entities.Invoice;
import com.facturacion.ideas.api.entities.ValueInvoice;
import com.facturacion.ideas.api.enums.TypeDocumentEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class DocumentMapperImpl implements IDocumentMapper {

	@Override
	public Invoice mapperToEntity(InvoiceNewDTO invoiceNewDTO) throws ParseException {

		Invoice invoice = new Invoice();
		invoice.setIde(invoiceNewDTO.getIde());
		invoice.setTypeDocument(TypeDocumentEnum.getTypeDocumentEnum(invoiceNewDTO.getTypeDocument()));
		invoice.setNumberSecuencial(invoiceNewDTO.getNumberSecuencial());
		invoice.setKeyAccess(invoiceNewDTO.getKeyAccess());
		// Es igual al keyAccess
		invoice.setNumberAutorization(invoiceNewDTO.getKeyAccess());

		invoice.setDateAutorization(FunctionUtil.convertStringToDate(invoiceNewDTO.getDateAutorization()));
		invoice.setDateEmission(FunctionUtil.convertStringToDate(invoiceNewDTO.getDateEmission()));
		invoice.setTypoEmision(invoiceNewDTO.getTypoEmision());
		return invoice;
	}

	@Override
	public InvoiceResposeDTO mapperToDTO(Invoice invoice) {

		InvoiceResposeDTO invoiceResposeDTO = new InvoiceResposeDTO();
		invoiceResposeDTO.setIde(invoice.getIde());
		invoiceResposeDTO.setTypeDocument(TypeDocumentEnum.getTypeDocumentEnum(invoice.getTypeDocument()).name());
		invoiceResposeDTO.setNumberSecuencial(invoice.getNumberSecuencial());
		invoiceResposeDTO.setKeyAccess(invoice.getKeyAccess());
		// Es igual al keyAccess
		invoiceResposeDTO.setNumberAutorization(invoice.getKeyAccess());

		invoiceResposeDTO.setDateAutorization(FunctionUtil.convertDateToString(invoice.getDateAutorization()));
		invoiceResposeDTO.setDateEmission(FunctionUtil.convertDateToString(invoice.getDateEmission()));
		invoiceResposeDTO.setTypoEmision(TypeEmissionEnum.getTypeEmissionEnum(invoice.getTypoEmision()).name());
		return invoiceResposeDTO;
	}

	@Override
	public List<InvoiceResposeDTO> mapperToDTO(List<Invoice> invoices) {

		List<InvoiceResposeDTO> invoiceResposeDTOs = new ArrayList<>();

		if (invoices.size() > 0) {
			invoiceResposeDTOs = invoices.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}
		return invoiceResposeDTOs;
	}

	@Override
	public ValueInvoice mapperToEntity(ValueInvoiceNewDTO valueInvoiceNewDTO) {

		ValueInvoice valueInvoice = new ValueInvoice();

		valueInvoice.setIde(valueInvoiceNewDTO.getIde());
		valueInvoice.setSubtIvaActual(valueInvoiceNewDTO.getSubtIvaActual());
		valueInvoice.setSubtIvaCero(valueInvoiceNewDTO.getSubtIvaCero());
		valueInvoice.setSubtNoObjIva(valueInvoiceNewDTO.getSubtNoObjIva());
		valueInvoice.setSubtExceptoIva(valueInvoiceNewDTO.getSubtExceptoIva());
		valueInvoice.setSubtotal(valueInvoiceNewDTO.getSubtotal());
		valueInvoice.setDescuento(valueInvoiceNewDTO.getDescuento());
		valueInvoice.setIce(valueInvoiceNewDTO.getIce());
		valueInvoice.setRbpnr(valueInvoiceNewDTO.getRbpnr());
		valueInvoice.setIva(valueInvoiceNewDTO.getIva());
		valueInvoice.setPropina(valueInvoiceNewDTO.getPropina());
		valueInvoice.setTotal(valueInvoiceNewDTO.getTotal());
		return valueInvoice;
	}

	@Override
	public ValueInvoiceResponseDTO mapperToDTO(ValueInvoice valueInvoice) {

		ValueInvoiceResponseDTO valueInvoiceResponseDTO = new ValueInvoiceResponseDTO();

		valueInvoiceResponseDTO.setIde(valueInvoice.getIde());
		valueInvoiceResponseDTO.setSubtIvaActual(valueInvoice.getSubtIvaActual());
		valueInvoiceResponseDTO.setSubtIvaCero(valueInvoice.getSubtIvaCero());
		valueInvoiceResponseDTO.setSubtNoObjIva(valueInvoice.getSubtNoObjIva());
		valueInvoiceResponseDTO.setSubtExceptoIva(valueInvoice.getSubtExceptoIva());
		valueInvoiceResponseDTO.setSubtotal(valueInvoice.getSubtotal());
		valueInvoiceResponseDTO.setDescuento(valueInvoice.getDescuento());
		valueInvoiceResponseDTO.setIce(valueInvoice.getIce());
		valueInvoiceResponseDTO.setRbpnr(valueInvoice.getRbpnr());
		valueInvoiceResponseDTO.setIva(valueInvoice.getIva());
		valueInvoiceResponseDTO.setPropina(valueInvoice.getPropina());
		valueInvoiceResponseDTO.setTotal(valueInvoice.getTotal());
		return valueInvoiceResponseDTO;
	}

}
