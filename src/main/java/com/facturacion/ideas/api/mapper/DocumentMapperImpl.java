package com.facturacion.ideas.api.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.util.ConstanteUtil;
import org.springframework.stereotype.Component;

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
        invoice.setGuiaRemission(invoiceNewDTO.getRemissionGuideNumber());

        // Asignar lista con formas de pagos
        invoice.setDetailsInvoicePayments(mapperToEntity(invoiceNewDTO.getPaymenNewtDTOS()));

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
        invoiceResposeDTO.setRemissionGuideNumber(invoice.getGuiaRemission());
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

    @Override
    public ValueInvoiceNewDTO mapperToNewDTO(ValueInvoice valueInvoice) {
        ValueInvoiceNewDTO valueInvoiceNew = new ValueInvoiceNewDTO();
        valueInvoiceNew.setIde(valueInvoice.getIde());
        valueInvoiceNew.setSubtIvaActual(valueInvoice.getSubtIvaActual());
        valueInvoiceNew.setSubtIvaCero(valueInvoice.getSubtIvaCero());
        valueInvoiceNew.setSubtNoObjIva(valueInvoice.getSubtNoObjIva());
        valueInvoiceNew.setSubtExceptoIva(valueInvoice.getSubtExceptoIva());
        valueInvoiceNew.setSubtotal(valueInvoice.getSubtotal());
        valueInvoiceNew.setDescuento(valueInvoice.getDescuento());
        valueInvoiceNew.setIce(valueInvoice.getIce());
        valueInvoiceNew.setRbpnr(valueInvoice.getRbpnr());
        valueInvoiceNew.setIva(valueInvoice.getIva());
        valueInvoiceNew.setPropina(valueInvoice.getPropina());
        valueInvoiceNew.setTotal(valueInvoice.getTotal());
        return valueInvoiceNew;
    }

    @Override
    public DeatailsInvoiceProduct mapperToEntity(DeatailsInvoiceProductDTO deatailsInvoiceProductDTO) {
        DeatailsInvoiceProduct detaails = new DeatailsInvoiceProduct();
        detaails.setIde(deatailsInvoiceProductDTO.getIde());
        detaails.setAmount(deatailsInvoiceProductDTO.getAmount());
        detaails.setSubtotal(deatailsInvoiceProductDTO.getSubtotal());
        return detaails;
    }

    @Override
    public List<DetailsInvoicePayment> mapperToEntity(List<PaymenNewtDTO> paymenNewtDTOS) {

        if (paymenNewtDTOS == null) return new ArrayList<>();

        List<DetailsInvoicePayment> pagos = new ArrayList<>();
        paymenNewtDTOS.forEach(item -> {

            DetailsInvoicePayment detailsInvoicePayment = new DetailsInvoicePayment();

            detailsInvoicePayment.setPayment(new Payment(item.getCode()));


            pagos.add(detailsInvoicePayment);
        });

        return pagos;
    }

    @Override
    public ComprobantesResponseDTO mapperComprobanteToDTO(Invoice invoice) {

        ComprobantesResponseDTO comprobante = new ComprobantesResponseDTO();
        comprobante.setIdComprobante(invoice.getIde());
        comprobante.setFechaEmision(FunctionUtil.convertDateToStringSRI(invoice.getDateEmission()));
        comprobante.setTipoDocumento(TypeDocumentEnum.getTypeDocumentEnum(invoice.getTypeDocument()).getDescription());

        Person person = invoice.getPerson();

        if (person != null) {

            comprobante.setNombreCliente(person.getRazonSocial());
            comprobante.setNumeroIdentificacion(person.getNumeroIdentificacion());
        } else {
            comprobante.setNombreCliente(ConstanteUtil.TEXT_DEFAULT_CONSUMIDOR_FINAL);
            comprobante.setNumeroIdentificacion(ConstanteUtil.TEXT_DEFAULT_CODE_CONSUMIDOR_FINAL);
        }
        comprobante.setTotalDocumento(invoice.getValueInvoice().getTotal());
        return comprobante;
    }

    @Override
    public List<ComprobantesResponseDTO> mapperComprobanteToDTO(List<Invoice> invoice) {

        if (invoice.size() > 0) {
            return invoice.stream().map(this::mapperComprobanteToDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();

    }

}
