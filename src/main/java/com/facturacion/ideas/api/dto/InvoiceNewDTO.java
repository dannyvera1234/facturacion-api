package com.facturacion.ideas.api.dto;

import java.util.ArrayList;
import java.util.List;

public class InvoiceNewDTO extends DocumentNewDTO {

    private static final long serialVersionUID = 1L;

    private String remissionGuideNumber;

    private List<DeatailsInvoiceProductDTO> deatailsInvoiceProductDTOs;

    private ValueInvoiceNewDTO valueInvoiceNewDTO;

    private List<PaymenNewtDTO> paymenNewtDTOS;

    public InvoiceNewDTO() {

        super();
        deatailsInvoiceProductDTOs = new ArrayList<>();
        paymenNewtDTOS = new ArrayList<>();
    }

    public InvoiceNewDTO(Long ide, String typeDocument, String numberSecuencial, String keyAccess,
                         String numberAutorization, String dateAutorization, String dateEmission, String typoEmision, Long idPerson,
                         Long idEmissionPoint, String remissionGuideNumber) {
        super(ide, typeDocument, numberSecuencial, keyAccess, numberAutorization, dateAutorization, dateEmission,
                typoEmision, idPerson, idEmissionPoint);
        this.remissionGuideNumber = remissionGuideNumber;
        deatailsInvoiceProductDTOs = new ArrayList<>();
        paymenNewtDTOS = new ArrayList<>();
    }

    public String getRemissionGuideNumber() {
        return remissionGuideNumber;
    }

    public void setRemissionGuideNumber(String remissionGuideNumber) {
        this.remissionGuideNumber = remissionGuideNumber;
    }

    public ValueInvoiceNewDTO getValueInvoiceNewDTO() {
        return valueInvoiceNewDTO;
    }

    public void setValueInvoiceNewDTO(ValueInvoiceNewDTO valueInvoiceNewDTO) {
        this.valueInvoiceNewDTO = valueInvoiceNewDTO;
    }

    public List<DeatailsInvoiceProductDTO> getDeatailsInvoiceProductDTOs() {
        return deatailsInvoiceProductDTOs;
    }

    public void addDeatailsInvoiceProductDTOs(DeatailsInvoiceProductDTO deatailsInvoiceProductDTO) {
        this.deatailsInvoiceProductDTOs.add(deatailsInvoiceProductDTO);
    }

    public List<PaymenNewtDTO> getPaymenNewtDTOS() {
        return paymenNewtDTOS;
    }

    public void addPaymenNewtDTO(PaymenNewtDTO paymenNewtDTO) {
        this.paymenNewtDTOS.add(paymenNewtDTO);
    }

    @Override
    public String toString() {
        return "InvoiceNewDTO{" +
                "remissionGuideNumber='" + remissionGuideNumber + '\'' +
                ", deatailsInvoiceProductDTOs=" + deatailsInvoiceProductDTOs +
                ", valueInvoiceNewDTO=" + valueInvoiceNewDTO +
                ", paymenNewtDTOS=" + paymenNewtDTOS +
                "} " + super.toString();
    }
}
